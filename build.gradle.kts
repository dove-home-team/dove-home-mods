import kotlin.io.path.createDirectories

plugins {
    `java-library`
    `maven-publish`
    id("net.neoforged.moddev") version("2.0.107")
    id("com.modrinth.minotaur") version "2.+"
    idea
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://maven.minecraftforge.net/") {
            content {
                includeGroup("com.github.glitchfiend")
            }
        }
        maven("https://www.jitpack.io")
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
        maven {
            url = uri("http://maven.dragons.plus/releases")
            isAllowInsecureProtocol = true
        } // Ponder, Flywheel
        maven("https://maven.createmod.net") {
            content {
                includeGroup("dev.engine-room.flywheel")
                includeGroup("dev.engine-room.vanillin")
                includeGroup("com.simibubi.create")
                includeGroup("net.createmod.ponder")
            }
        } // Ponder, Flywheel
        maven("https://maven.ithundxr.dev/snapshots")
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
        maven("https://thedarkcolour.github.io/KotlinForForge/")
        flatDir {
            dir(rootProject.file("libs"))
        }
    }
    apply(plugin = "com.modrinth.minotaur")

    modrinth {
        token.set(System.getenv("MODRINTH_TOKEN"))
    }
}

val parchmentMinecraftVersion: String by rootProject
val parchmentMappingsVersion: String by rootProject
val neoVersion: String by rootProject
val loaderVersionRange: String by rootProject
val minecraftVersion: String by rootProject
val minecraftVersionRange: String by rootProject
val modLicense: String by rootProject
val modGroupId: String by rootProject

subprojects {
    rootProject.file("src/${project.name}").mkdirs()
    apply(plugin = "net.neoforged.moddev")
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "idea")
    val modVersion: String by project

    val modId: String by project
    val modName: String by project
    val modAuthors: String by project
    val modDescription: String by project


    version = modVersion
    group = modGroupId

    base {
        archivesName = modId
    }

    java.toolchain.languageVersion = JavaLanguageVersion.of(21)

    val modrinth: String by project
    val type: String by project
    val gameVersions: String by project
    val loaders: String by project

    modrinth {
        projectId.set(modrinth)
        versionNumber.set(modVersion)
        versionType.set(type)
        uploadFile.set(tasks.jar)
        this.gameVersions.set(gameVersions.split(","))
        this.loaders.set(loaders.split(","))
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

                gameDirectory.set(layout.buildDirectory.dir("run-client").get().asFile.apply {
                    this.mkdirs()
                })
                systemProperty("neoforge.enabledGameTestNamespaces", modId)
            }
            register("server") {
                server()
                gameDirectory.set(layout.buildDirectory.dir("run-server").get().asFile.apply {
                    this.mkdirs()
                })
                programArgument("--nogui")
                systemProperty("neoforge.enabledGameTestNamespaces", modId)
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
                logLevel = org.slf4j.event.Level.DEBUG
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

    dependencies {
        compileOnly("org.projectlombok:lombok:1.18.38")
        annotationProcessor("org.projectlombok:lombok:1.18.38")
    }

    publishing {
        publications {
            register<MavenPublication>("mavenJava") {
                from(components["java"])
            }
            repositories {
                maven {
                    url = uri("file://${project.layout.buildDirectory.dir("repo").get().asFile.absolutePath}")
                }
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
