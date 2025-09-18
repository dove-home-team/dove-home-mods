import org.jetbrains.kotlin.gradle.dsl.JvmTarget.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    `java-library`
}

repositories {
    maven {
        url = uri("https://maven.aliyun.com/repository/public/")
    }
    gradlePluginPortal()
    mavenCentral()
    maven {
        name = "MinecraftForge"
        url = uri("https://maven.minecraftforge.net/")
    }
    maven {
        name = "Sponge Maven"
        url = uri("https://repo.spongepowered.org/repository/maven-public/")
    }
    maven {
        name = "ParchmentMC"
        url = uri("https://maven.parchmentmc.org")
    }
    maven {
        name = "NeoForge"
        url = uri("https://maven.neoforged.net/releases/")
    }
}



dependencies {
    implementation(gradleApi())
    implementation("cn.hutool:hutool-json:5.8.40")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-toml:2.17.2")
    implementation("cn.hutool:hutool-setting:5.8.40")
    implementation("cn.hutool:hutool-http:5.8.40")
    compileOnly("net.neoforged:moddev-gradle:2.0.+")
}

tasks.withType<KotlinCompile> {
    compilerOptions {
        jvmTarget.set(JVM_21)
    }
}
