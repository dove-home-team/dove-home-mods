import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import net.neoforged.moddevgradle.dsl.RunModel
import org.slf4j.event.Level
import java.util.jar.JarFile
import kotlin.io.path.createDirectories

plugins {
    `java-library`
    `maven-publish`
    mdg
    minotaur
    idea
}

allprojects {
    repositories.mavenSettings(project)
    `minotaur-apply`

    modrinth {
        token.set(System.getenv("MODRINTH_TOKEN"))
    }
}

fun RunModel.configureCommonRun(modId: String) {
    gameDirectory.set(project.layout.buildDirectory.dir("run-${this.name}").get().asFile.apply {
        this.mkdirs()
    })
    systemProperty("neoforge.enabledGameTestNamespaces", modId)
}

subprojects {
    rootProject.file("src/${project.name}").mkdirs()
    `mdg-apply`
    `jl-apply`
    `mp-apply`
    `idea-apply`

    version = modVersion
    group = modGroupId

    base {
        archivesName = modId
    }

    java.toolchain.languageVersion = JavaLanguageVersion.of(21)
    modrinth {
        setModrinth(projectId, versionNumber, versionType, uploadFile, gameVersions, loaders)
    }


    neoForge {
        version = neoVersion

        parchment {
            mappingsVersion = parchmentMappingsVersion
            minecraftVersion = parchmentMinecraftVersion
        }



        runs {
            register("client") {
                client()
                configureCommonRun(modId)
            }
            register("server") {
                server()
                configureCommonRun(modId)
                programArgument("--nogui")

            }
            register("data") {
                data()
                gameDirectory.set(layout.buildDirectory.dir("run-data").get().asFile.apply {
                    this.mkdirs()
                })
                programArguments
                    .addAll(
                        "--mod", modId,
                        "--all",
                        "--output", rootProject.file("src/${project.name}/generated").absolutePath,
                        "--existing", rootProject.file("src/${project.name}/resources").absolutePath)
            }

            configureEach {
                systemProperty("forge.logging.makers", "REGISTRIES")
                logLevel = Level.DEBUG
            }
        }

        mods {
            register(modId) {
                sourceSet(sourceSets.main.get())
            }
        }
        setAccessTransformers(rootProject.file("src/${project.name}/resources/META-INF/accesstransformer.cfg"))
    }

    val gmm = tasks.register<ProcessResources>("gmm") {
        val replaceProps = mapOf(
            "minecraft_version" to minecraftVersion,
            "minecraft_version_range" to minecraftVersionRange,
            "neo_version" to neoVersion,
            "loader_version_range" to loaderVersionRange,
            "mod_id" to modId,
            "mod_name" to modName,
            "mod_license" to modLicense,
            "mod_authors" to modAuthors,
            "mod_version" to modVersion,
            "mod_description" to modDescription,
        )
        inputs.properties(replaceProps)
        expand(replaceProps)
        from(rootProject.file("src/main/templates").absolutePath)
        into("build/generated/sources/modMetadata")
    }

    neoForge.ideSyncTask(gmm)

    sourceSets.main {
        resources {
            srcDir(rootProject.file("src/${project.name}/resources"))
            srcDir(rootProject.file("src/${project.name}/generated").toPath().createDirectories().toFile())
            srcDir(gmm.get())
        }
        java {
            srcDir(rootProject.file("src/${project.name}/java"))
        }
    }

    val localRuntime by configurations.creating {
        configurations.runtimeClasspath.get().extendsFrom(this)
    }
    val modImplementation by configurations.creating {
        configurations.implementation.get().extendsFrom(this)
    }

    dependencies {
        compileOnly("org.projectlombok:lombok:1.18.38")
        annotationProcessor("org.projectlombok:lombok:1.18.38")
        compileOnly("mezz.jei:jei-1.21.1-neoforge-api:+")
        "localRuntime"("mezz.jei:jei-1.21.1-neoforge:+")
        "modImplementation"("me.shedaniel.cloth:cloth-config-neoforge:15.0.140")
    }

    publishing {
        publications {
            register<MavenPublication>("mavenJava") {
                from(components["java"])
            }
            repositories {
//                maven {
//                    url = uri("file://${project.layout.buildDirectory.dir("repo").get().asFile.absolutePath}")
//                }
            }
        }
    }

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
    }

    idea {
        module {
            isDownloadJavadoc = true
            isDownloadSources = true
        }
    }

    tasks.build {
        dependsOn(tasks.getByName("runData"))
    }
}


tasks.build {
    for (it in subprojects) {
        dependsOn(it.project.tasks.build.get())

    }
}

tasks.register("gmm") {
    for (it in subprojects) {
        dependsOn(it.project.tasks.getByName("gmm"))
    }
}

val extractAllDependencies = extractAllDependencies()
val changeLogExtract = changeLogExtract()

//tasks.register("test1") {
//    subprojects.forEach {
//        it.configurations.runtimeClasspath.get().allDependencies.forEach {
//            println(it.name)
//            println(it.version)
//        }
//    }
//}
//val extractAllDependencies by tasks.registering(Copy::class) {
//    var extractDir = layout.buildDirectory.dir("extract").get()
//    var changelog = file("changelog.md")
//    if (changelog.exists().not()) {
//        changelog.createNewFile()
//    }
//    extractDir.asFile.mkdirs()
//    var profile = extractDir.file("profile.json").asFile
//    if (profile.exists().not()) {
//        profile.bufferedWriter(Charsets.UTF_8).use {
//            gson.toJson(Data(arrayListOf()), it)
//        }
//    }
//    val data = profile.bufferedReader(Charsets.UTF_8).use {
//        gson.fromJson(it, Data::class.java)!!
//    }
//    var destDir = extractDir.dir("mods").asFile
//    destDir.deleteRecursively()
//    val uniqueJars = subprojects
//        .flatMap { p ->
//            p.configurations.runtimeClasspath.get().files
//        }
//        .distinctBy { it.name }
//        .toMutableList()
//    var jarFile: JarFile
//    uniqueJars.removeIf {
//        jarFile = JarFile(it)
//        val jarEntry = jarFile.getJarEntry("META-INF/neoforge.mods.toml")
//        var b = jarEntry == null
//        jarFile.close()
//        b
//    }
//
//    from(uniqueJars)
//    into(destDir)
//    exclude("neoforge-**.jar")
//}
