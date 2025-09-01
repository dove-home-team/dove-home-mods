pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        maven("https://maven.neoforged.net/releases")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention").version("0.9.0")
}

include("lib","coremod", /*"staff",*/ "dweomercraft", "formation-master", "pack")
include("act")
include("java-annotation", "java-processor")


rootProject.name = "dove"