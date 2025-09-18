import org.gradle.api.Project
import org.gradle.api.plugins.PluginAware
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

/**
 * @author baka4n
 * @code @Date 2025/9/18 15:44:41
 */
val PluginDependenciesSpec.mdg: PluginDependencySpec
    inline get() {
        return id("net.neoforged.moddev").version("2.0.+")
    }
val PluginDependenciesSpec.minotaur: PluginDependencySpec
    inline get() {
        return id("com.modrinth.minotaur").version("2.+")
    }

val PluginAware.`mdg-apply`: Unit
    inline get() {
        return this.apply(plugin = "net.neoforged.moddev")
    }
val PluginAware.`mp-apply`: Unit
    inline get() {
        return this.apply(plugin = "maven-publish")
    }
val PluginAware.`jl-apply`: Unit
    inline get() {
        return this.apply(plugin = "java-library")
    }
val PluginAware.`idea-apply`: Unit
    inline get() {
        return this.apply(plugin = "idea")
    }
val PluginAware.`minotaur-apply`: Unit
    inline get() {
        return this.apply(plugin = "com.modrinth.minotaur")
    }

val Project.parchmentMinecraftVersion: String
    inline get() {
        val parchmentMinecraftVersion: String by rootProject
        return parchmentMinecraftVersion
    }
val Project.parchmentMappingsVersion: String
    inline get() {
        val parchmentMappingsVersion: String by rootProject
        return parchmentMappingsVersion
    }
val Project.neoVersion: String
    inline get() {
        val neoVersion: String by rootProject
        return neoVersion
    }
val Project.loaderVersionRange: String
    inline get() {
        val loaderVersionRange: String by rootProject
        return loaderVersionRange
    }
val Project.minecraftVersion: String
    inline get() {
        val minecraftVersion: String by rootProject
        return minecraftVersion
    }
val Project.minecraftVersionRange: String
    inline get() {
        val minecraftVersionRange: String by rootProject
        return minecraftVersionRange
    }
val Project.modLicense: String
    inline get() {
        val modLicense: String by rootProject
        return modLicense
    }
val Project.modGroupId: String
    inline get() {
        val modGroupId: String by rootProject
        return modGroupId
    }
val Project.modVersion: String
    inline get() {
        val modVersion: String by this
        return modVersion
    }
val Project.modId: String
    inline get() {
        val modId: String by this
        return modId
    }
val Project.modName: String
    inline get() {
        val modName: String by this
        return modName
    }
val Project.modAuthors: String
    inline get() {
        val modAuthors: String by this
        return modAuthors
    }
val Project.modDescription: String
    inline get() {
        val modDescription: String by this
        return modDescription
    }
val Project.modrinth: String
    inline get() {
        val modrinth: String by this
        return modrinth
    }
val Project.type: String
    inline get() {
        val type: String by this
        return type
    }
val Project.gameVersions: String
    inline get() {
        val gameVersions: String by this
        return gameVersions
    }
val Project.loaders: String
    inline get() {
        val loaders: String by this
        return loaders
    }

val Project.gameVersionsR: List<String> get() = gameVersions.split(",")
val Project.loadersR: List<String> get() = loaders.split(",")

fun <T: Any> T.set(v: Property<T>) {
    v.set(this)
}

fun <T: Any> List<T>.set(v: ListProperty<T>) {
    v.set(this)
}

fun Project.setModrinth(
    projectId: Property<String>,
    versionNumber: Property<String>,
    versionType: Property<String>,
    uploadFile: Property<Any>?,
    gameVersions: ListProperty<String>,
    loaders: ListProperty<String>,
) {
    modrinth.set(projectId)
    modVersion.set(versionNumber)
    type.set(versionType)
    tasks.getByName<Jar>("jar").set(uploadFile!!)
    gameVersionsR.set(gameVersions)
    loadersR.set(loaders)
}








