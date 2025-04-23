import org.gradle.accessors.dm.LibrariesForLibs
import org.slf4j.event.Level
import java.time.LocalDateTime


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

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

allprojects {
    apply(plugin = "idea")
    apply(plugin = "maven-publish")
    apply(plugin = "java-library")
    if (project.name.contains("java").not() && project != rootProject) {
        apply(plugin = "net.neoforged.moddev")
        version = modVersion
            .replace("{MC}", mcVersion)
            .replace("{YEAR}", date.year.toString())
            .replace("{MONTH}", date.monthValue.toString())
            .replace("{DAY}", date.dayOfMonth.toString())
            .replace("{MINUTE}", date.minute.toString())
            .replace("{SECOND}", date.second.toString())
        group = modGroupId
    }
    repositories {
        mavenLocal()
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
    base {
        archivesName = modid + "_" + project.name
    }
    if (project.name.contains("java").not().and(project!=rootProject)) {
        neoForge {
            version = neoVersion
            parchment {
                mappingsVersion = parchmentMappingsVersion
                minecraftVersion = parchmentMinecraftVersion.replace("{mcVersion}", mcVersion)
            }
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
                        "--output", file("src/generated/resources/").absolutePath,
                        "--existing", file("src/main/resources/").absolutePath
                    ))
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
            sourceSets.main {
                java {
                    srcDir("build/generated/sources/main/java")
                }
                resources {
                    srcDir("src/generated/resources/")

                }
            }

            val generateSimpleClasses by tasks.registering {
                val packageName = "io.github.dovehometeam.dove" + project.name
                val outputDir = file("build/generated/sources/main/java/${packageName.replace(".", "/")}")
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

            tasks.compileJava {
                dependsOn(generateSimpleClasses)
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

        dependencies {
            lib.findBundle(base.archivesName.get() + "_compileOnly").ifPresentOrElse({
                compileOnly(it)
            }, {
                logger.error("Cannot find bundle: ${base.archivesName.get()}_compileOnly")
            })

            lib.findBundle(base.archivesName.get() + "_runtimeOnly").ifPresentOrElse({
                runtimeOnly(it)
            }, {
                logger.error("Cannot find bundle: ${base.archivesName.get()}_runtimeOnly")
            })
            lib.findBundle(base.archivesName.get() + "_linkage").ifPresentOrElse({
                compileOnly(it)
                runtimeOnly(it)
            }, {
                logger.error("Cannot find bundle: ${base.archivesName.get()}_linkage")
            })
            lib.findBundle(base.archivesName.get() + "_prepose").ifPresentOrElse({
                implementation(it)
            }, {
                logger.error("Cannot find bundle: ${base.archivesName.get()}_prepose")
            })
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
            resources {
                srcDir(generateModMetadata)
            }
        }
        neoForge.ideSyncTask(generateModMetadata)
    }
}