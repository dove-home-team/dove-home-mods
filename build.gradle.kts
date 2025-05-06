import org.slf4j.event.Level
import java.time.LocalDateTime
import java.util.*


plugins {
    idea
    `java-library`
    `maven-publish`
    alias(libs.plugins.mod.dev.gradle)
}

val mcVersion: String by rootProject
val mcVersionRange: String by rootProject
val modVersion: String by rootProject
val modGroupId: String by rootProject
val modid: String by rootProject
val neoVersion: String by rootProject
val neoVersionRange: String by rootProject
val loaderVersionRange: String by rootProject
val modLicense: String by rootProject
val modAuthors: String by rootProject
val parchmentMappingsVersion: String by rootProject
val parchmentMinecraftVersion: String by rootProject
val date: LocalDateTime = LocalDateTime.now()

tasks.named<Wrapper>("wrapper").configure {
    distributionType = Wrapper.DistributionType.BIN
}


allprojects {
    apply(plugin = "idea")
    apply(plugin = "maven-publish")
    apply(plugin = "java-library")
    java.toolchain.languageVersion = JavaLanguageVersion.of(21)
    if (project.name.contains("java").not() && project != rootProject) {
        apply(plugin = "net.neoforged.moddev")
        version = modVersion
            .replace("{MC}", mcVersion)
            .replace("{YEAR}", date.year.toString())
            .replace("{MONTH}", date.monthValue.toString())
            .replace("{DAY}", date.dayOfMonth.toString())
            .replace("{HOUR}", date.hour.toString())
            .replace("{MINUTE}", date.minute.toString())
            .replace("{SECOND}", date.second.toString())
        group = modGroupId
    }
    repositories {
        //www.curseforge.com/api/v1/mods/projectId/files/fileId/download
        //mediafilez.forgecdn.net/files/top_start4/top_other_num/downloadFileName
        mavenLocal()
        mavenCentral()
        maven("https://maven.minecraftforge.net/") {
            content {
                includeGroup("com.github.glitchfiend")
            }
        }
        maven("https://maven.shedaniel.me")
        maven("https://maven.crystaelix.com/releases/") {
            content {
                includeGroup("thelm.packagedmekemicals")
                includeGroup("thelm.packagedauto")
                includeGroup("thelm.packagedexcrafting")
            }
        }
        maven("https://jitpack.io") {
            content {
                includeGroup("com.github.rtyley")
            }
        }
        maven("https://beta.cursemaven.com") {
            content {
                includeGroup("curse.maven")
            }
        }
        maven("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/") {
            content {
                includeGroup("software.bernie.geckolib")
            }
        }
        maven("https://maven.neoforged.net/releases")
        maven("https://maven.parchmentmc.org")
        maven("https://maven.dragons.plus/releases") // Ponder, Flywheel
        maven("https://maven.createmod.net") // Ponder, Flywheel
        maven("https://mvn.devos.one/snapshots") // Registrate
        maven("https://maven.blamejared.com") // JEI
        maven("https://maven.theillusivec4.top/") // Curios API

        maven("https://api.modrinth.com/maven") {
            content {
                includeGroup("maven.modrinth")
            }
        }
        maven("https://raw.githubusercontent.com/Fuzss/modresources/main/maven") // NeoForge config api port, needed by ponder
        maven("https://maven.fallenbreath.me/releases") // Conditional Mixin
        maven("https://raw.github.com/0999312/MMMaven/main/repository")
        maven("https://modmaven.dev/artifactory/local-releases/")
        maven("https://maven.blakesmods.com/releases")
        maven("https://modmaven.dev/") {
            content {
                includeGroup("com.alexthw.ars_elemental")
            }
        }
        maven("https://maven.architectury.dev/")
        maven("https://maven.latvian.dev/releases") {
            content {
                includeGroup("dev.latvian.mods")
                includeGroup("dev.latvian.apps")
            }
        }
    }
    idea {
        module {
            isDownloadSources = true
            isDownloadJavadoc = true
        }
    }

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
    }
}
val lib = versionCatalogs.named("libs")

subprojects {

    var modidReplace: String
    try {
        modidReplace = properties.getOrDefault("modid", modid).toString()
    } catch (e: Exception) {
        modidReplace = modid
    }
    base {
        archivesName = if (modidReplace.equals(modid)) {
            (modid + "_" + project.name.replace("-", "_").lowercase(Locale.ROOT))
        } else {
            modidReplace
        }
    }
    if (project.name.contains("java").not().and(project!=rootProject)) {
        val copyToMerge = tasks.register<Copy>("copyToMerge") {
            into("${rootProject.file("build")}/libs")
            from(tasks.jar.get().outputs.files)
        }

        tasks.build {
            dependsOn(copyToMerge.get())
        }

        neoForge {
            version = neoVersion
            parchment {
                mappingsVersion = parchmentMappingsVersion
                minecraftVersion = parchmentMinecraftVersion.replace("{mcVersion}", mcVersion)
            }
            val dataPath = rootProject.file("src/${project.name}/generated").absolutePath
            runs {
                register("client") {
                    client()
                    gameDirectory.set(file("runs/client"))
                    systemProperty("neoforge.enabledGameTestNamespaces", base.archivesName.get())
                }
                register("server") {
                    server()
                    programArgument("--nogui")
                    gameDirectory.set(file("runs/server"))
                    systemProperty("neoforge.enabledGameTestNamespaces", base.archivesName.get())
                }
                register("gameTestServer") {
                    type = "gameTestServer"
                    systemProperty("neoforge.enabledGameTestNamespaces", base.archivesName.get())
                }
                register("data") {
                    data()
                    programArguments.addAll(listOf(
                        "--mod", base.archivesName.get(),
                        "--all",
                        "--output", dataPath,
                        "--existing", file("src/main/resources/").absolutePath
                    ))
                    environment("datagen", "true")
                }
                configureEach {
                    systemProperty("forge.logging.markers", "REGISTRIES")
                    logLevel = Level.DEBUG
                }
            }

            mods {
                register(base.archivesName.get()) {
                    sourceSet(sourceSets.main.get())
                }
            }


            val packageName = "io.github.dovehometeam." + base.archivesName.get().replace("_", "")
            val outputDir = file("build/generated/sources/main/java/${packageName.replace(".", "/")}")

            val generateSimpleClasses by tasks.registering {

                val genlist =rootProject.file("src/main/javagen")
                doLast {
                    outputDir.mkdirs()
                    genlist.listFiles()?.forEach {
                        file("${outputDir}/${it.name}").writeText(
                            it.readText()
                                .replace("{package_name}", packageName)
                                .replace("{modid}", base.archivesName.get())

                        )
                    }

                }
            }


            tasks.register<Exec>("testBatTask") {
                workingDir(file("./"))
                commandLine("cmd","/c","gradlew build")
            }

            if (outputDir.exists().not()) {
                tasks.neoForgeIdeSync {
                    dependsOn(tasks.build)
                }
            }



            tasks.compileJava {
                dependsOn(generateSimpleClasses)
                if(System.getenv("datagen") == null)
                    exclude(("$packageName.datagen").replace(".", "/"))
            }

        }
        publishing {
            publications {
                register<MavenPublication>("mavenJavaBy" + base.archivesName.get().uppercase()) {
                    from(components["java"])
                }
            }
            repositories {
                mavenLocal()

            }
        }
        val libsDir = rootProject.file("libs/${project.name}")
        libsDir.mkdirs()
        repositories {
            flatDir {
                dir(libsDir.absolutePath)
            }
        }

        dependencies {

            implementation(
                fileTree(baseDir = libsDir.absolutePath) {
                    include("*.jar")
                }
            )
            lib.findBundle(base.archivesName.get() + "_compileOnly").ifPresentOrElse({
                compileOnly(it)
            }, {
                logger.error("Cannot find bundle: ${base.archivesName.get()}_compileOnly")
            })
            lib.findBundle(base.archivesName.get() + "_compileOnly_transitive_false").ifPresentOrElse({
                compileOnly(it) {isTransitive = false }
            }, {
                logger.error("Cannot find bundle: ${base.archivesName.get()}_compileOnly_transitive_false")
            })


            lib.findBundle(base.archivesName.get() + "_runtimeOnly").ifPresentOrElse({
                runtimeOnly(it)
            }, {
                logger.error("Cannot find bundle: ${base.archivesName.get()}_runtimeOnly")
            })
            lib.findBundle(base.archivesName.get() + "_runtimeOnly_transitive_false").ifPresentOrElse({
                runtimeOnly(it) {isTransitive = false }
            }, {
                logger.error("Cannot find bundle: ${base.archivesName.get()}_runtimeOnly_transitive_false")
            })
            lib.findBundle(base.archivesName.get() + "_api").ifPresentOrElse({
                api(it)
            }, {
                logger.error("Cannot find bundle: ${base.archivesName.get()}_api")
            })
            lib.findBundle(base.archivesName.get() + "_api_transitive_false").ifPresentOrElse({
                api(it) {isTransitive = false }
            }, {
                logger.error("Cannot find bundle: ${base.archivesName.get()}_api_transitive_false")
            })


            lib.findBundle(base.archivesName.get() + "_linkage").ifPresentOrElse({
                compileOnly(it)
                runtimeOnly(it)
            }, {
                logger.error("Cannot find bundle: ${base.archivesName.get()}_linkage")
            })
            lib.findBundle(base.archivesName.get() + "_linkage_transitive_false").ifPresentOrElse({
                compileOnly(it) { isTransitive = false }
                runtimeOnly(it) {isTransitive = false }
            }, {
                logger.error("Cannot find bundle: ${base.archivesName.get()}_linkage_transitive_false")
            })


            lib.findBundle(base.archivesName.get() + "_prepose").ifPresentOrElse({
                implementation(it)
            }, {
                logger.error("Cannot find bundle: ${base.archivesName.get()}_prepose")
            })
            lib.findBundle(base.archivesName.get() + "_prepose_transitive_false").ifPresentOrElse({
                implementation(it) { isTransitive = false }
            }, {
                logger.error("Cannot find bundle: ${base.archivesName.get()}_prepose_transitive_false")
            })

            lib.findBundle(base.archivesName.get()+"_ap").ifPresentOrElse({
                annotationProcessor(it)
            }, {
                logger.error("Cannot find bundle: ${base.archivesName.get()}_ap")
            })
            lib.findBundle(base.archivesName.get()+"_ap_transitive_false").ifPresentOrElse({
                annotationProcessor(it) { isTransitive = false }
            }, {
                logger.error("Cannot find bundle: ${base.archivesName.get()}_ap_transitive_false")
            })

            lib.findBundle("all_compileOnly").ifPresentOrElse({
                compileOnly(it)
            }, {
                logger.error("Cannot find bundle: all_compileOnly")
            })
            lib.findBundle("all_compileOnly_transitive_false").ifPresentOrElse({
                compileOnly(it) { isTransitive = false }
            }, {
                logger.error("Cannot find bundle: all_compileOnly_transitive_false")
            })

            lib.findBundle("all_runtimeOnly").ifPresentOrElse({
                runtimeOnly(it)
            }, {
                logger.error("Cannot find bundle: all_runtimeOnly")
            })
            lib.findBundle("all_runtimeOnly_transitive_false").ifPresentOrElse({
                runtimeOnly(it) {isTransitive = false }
            }, {
                logger.error("Cannot find bundle: all_runtimeOnly_transitive_false")
            })
            lib.findBundle("all_api").ifPresentOrElse({
                api(it)
            }, {
                logger.error("Cannot find bundle: all_api")
            })
            lib.findBundle("all_api_transitive_false").ifPresentOrElse({
                api(it) {isTransitive = false }
            }, {
                logger.error("Cannot find bundle: all_api_transitive_false")
            })

            lib.findBundle("all_linkage").ifPresentOrElse({
                compileOnly(it)
                runtimeOnly(it)
            }, {
                logger.error("Cannot find bundle: all_linkage")
            })
            lib.findBundle("all_linkage_transitive_false").ifPresentOrElse({
                compileOnly(it) { isTransitive = false }
                runtimeOnly(it) { isTransitive = false }
            }, {
                logger.error("Cannot find bundle: all_linkage_transitive_false")
            })

            lib.findBundle("all_prepose").ifPresentOrElse({
                implementation(it)
            }, {
                logger.error("Cannot find bundle: all_prepose")
            })
            lib.findBundle("all_prepose_transitive_false").ifPresentOrElse({
                implementation(it) {isTransitive = false}
            }, {
                logger.error("Cannot find bundle: all_prepose_transitive_false")
            })

            lib.findBundle("all_ap").ifPresentOrElse({
                annotationProcessor(it)
            }, {
                logger.error("Cannot find bundle: all_ap")
            })
            lib.findBundle("all_ap_transitive_false").ifPresentOrElse({
                annotationProcessor(it) { isTransitive = false }
            }, {
                logger.error("Cannot find bundle: all_ap_transitive_false")
            })

            if (!project.name.contains("java")) {
                compileOnly(project(":java-annotation"))
                annotationProcessor(project(":java-processor"))
            }
        }

        val generateModMetadata = tasks.register<ProcessResources>("generateModMetadata") {
            val replaceProperties = mapOf(
                "minecraft_version" to mcVersion,
                "minecraft_version_range" to mcVersionRange,
                "neo_version" to neoVersion,
                "neo_version_range" to neoVersionRange,
                "loader_version_range" to loaderVersionRange,
                "mod_id" to base.archivesName.get(),
                "mod_name" to base.archivesName.get(),
                "mod_license" to modLicense,
                "mod_version" to project.version.toString(),
                "mod_authors" to modAuthors,
                "mod_description" to base.archivesName.get()
            )
            inputs.properties(replaceProperties)
            expand(replaceProperties)
            from(rootProject.file("src/main/templates"))
            into("build/generated/sources/modMetadata")
        }
        sourceSets.main {
            java {
                srcDir("build/generated/sources/main/java")
                srcDir(rootProject.file("src/${project.name}/java"))
            }
            resources {
                srcDir(generateModMetadata)
                srcDir(rootProject.file("src/${project.name}/generated"))
                srcDir(rootProject.file("src/${project.name}/resources"))
            }
        }
        neoForge.ideSyncTask(generateModMetadata)
    }

}

//val clearLib = tasks.register<Delete>("clearLib") {
//    delete("build/libs/**")
//}

val buildAll by tasks.registering {
    subprojects.forEach {
        if (it.name.contains("java").not()) {
            dependsOn(it.tasks.build)
        }
    }
}