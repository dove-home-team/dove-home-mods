import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.file.RegularFile
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.TaskContainer
import org.gradle.kotlin.dsl.RegisteringDomainObjectDelegateProviderWithTypeAndAction
import org.gradle.kotlin.dsl.registering

/**
 * @author baka4n
 * @code @Date 2025/9/18 13:18:22
 */
fun Project.getExtractDir(): Directory {
    return layout.buildDirectory.dir("extract").get().mkdirs()
}

fun Project.getChangelogDir(): Directory {
    return getExtractDir().dir("changelog").mkdirs()
}

fun Directory.mkdirs(): Directory {
    this.asFile.mkdirs()
    return this
}

fun Project.getModsDir(): Directory {
    return getExtractDir().dir("mods").mkdirs();
}

fun Project.extractAllDependencies(): RegisteringDomainObjectDelegateProviderWithTypeAndAction<out TaskContainer, Copy> {
    val extractAllDependencies = tasks.registering(Copy::class) {
        val extractDir = getExtractDir()
        val changelogDir = getChangelogDir()
        val modsDir = getModsDir()
    }
    return extractAllDependencies
}