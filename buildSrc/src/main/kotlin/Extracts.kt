import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.dataformat.toml.TomlFactory
import com.fasterxml.jackson.dataformat.toml.TomlFactoryBuilder
import com.fasterxml.jackson.dataformat.toml.TomlMapper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.file.Directory
import org.gradle.api.file.RegularFile
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.register
import java.io.File
import java.util.*
import java.util.jar.JarFile
import kotlin.time.Clock.System.now
import kotlin.time.ExperimentalTime

val gson: Gson = GsonBuilder().setPrettyPrinting().create()!!

/**
 * @author baka4n
 * @code @Date 2025/9/18 13:18:22
 */
fun Project.getExtractDir(): Directory {
    return layout.buildDirectory.dir("extract").get().mkdirs()
}

fun Project.getChangelog(): File {
    return rootProject.file("changelog.md")
//    return getExtractDir().file()
}

fun Project.profileJson(): RegularFile {
    return getExtractDir().file("profile.json")
}



fun Directory.mkdirs(): Directory {
    asFile.mkdirs()
    return this
}

fun Project.getModsDir(): Directory {
    return getExtractDir().dir("mods")
}

@OptIn(ExperimentalTime::class)
fun Project.changeLogExtract(): TaskProvider<Task> {

    return tasks.register("changeLogExtract") {
        val extractAllDependencies = tasks.getByName("extractAllDependencies")
        dependsOn(extractAllDependencies)
        val modListChange = "\u115F"// 模组列表替换线，空白字符
        val extractDir = getExtractDir()
        val changelog = getChangelog()
        var changeLogTemp: String
        if (!changelog.exists()) {
            changeLogTemp = """
                # mods changes
                $modListChange
                # updates
            """.trimIndent()
        } else {
            changeLogTemp = changelog.readText(Charsets.UTF_8)
        }

        val modsDir = getModsDir()
        val profile = profileJson()

        val data: Data
        if (profile.asFile.exists()) {
            data = gson.fromJson(profile.asFile.readText(Charsets.UTF_8), Data::class.java)
        } else {
            data = Data()
        }

        for (modFile in modsDir.asFile.listFiles()) {
            val jarFile = JarFile(modFile)
            val jarEntry = jarFile.getJarEntry("META-INF/neoforge.mods.toml")
            logger.info("{}", jarFile)
            if (jarEntry != null) {
                val inputStream = jarFile.getInputStream(jarEntry)
                inputStream.use {
                    val mapper = TomlMapper()
                    val readTree = mapper.readTree(it)
                    readTree.get("mods").forEach { mod ->
                        val key = mod.get("modId").textValue()
                        var value = mod.get("version").textValue()
                        if (value.contains("\${file.jarVersion}")) {
                            value = jarFile.manifest.mainAttributes.getValue("Implementation-Version")
                        }
                        val dataTime = now().toString().substring(0, 10)
                        if (!data.mods.containsKey(key)) {
                            data.mods[key] = value
                            changeLogTemp = changeLogTemp.replace(modListChange, "- modId:$key,version:$value,updated:${dataTime}\n${modListChange}")
                        } else {
                            if (!value.contains(data.mods[key]!!)) {
                                data.mods[key] = value
                                changeLogTemp = changeLogTemp.replace(modListChange, "- modId:$key,version:$value,updated:${dataTime}\n${modListChange}")
                            }
                        }
                    }
//                    println(parse.get("mods"))
//                    println(parse.get("mods.version"))
                }
                inputStream.close()
            }
            jarFile.close()

        }

        changelog.writeText(changeLogTemp, Charsets.UTF_8)
        profile.asFile.writeText(gson.toJson(data), Charsets.UTF_8)
    }

}

fun Project.extractAllDependencies(): TaskProvider<Copy> {
    return tasks.register<Copy>("extractAllDependencies") {
        val modsDir = getModsDir()

        modsDir.mkdirs()
        val uniqueJars = subprojects
            .flatMap {
                it.configurations["runtimeClasspath"].files
            }
            .distinctBy { it.name }
            .toMutableList()
        uniqueJars.removeIf {
            val jarFile = JarFile(it)
            val jarEntry = jarFile.getJarEntry("META-INF/neoforge.mods.toml")
            val b = jarEntry == null
            jarFile.close()
            b
        }
        from(uniqueJars)
        into(modsDir)
        exclude("neoforge-**.jar")
    }
}