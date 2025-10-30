import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven

/**
 * @author baka4n
 * @code @Date 2025/9/18 15:30:41
 */

fun Project.mavenSettings() {
    repositories
        .local()
        .central()
        .mavenUrlSimple("https://maven.minecraftforge.net/", "com.github.glitchfiend")
        .mavenUrlSimple("https://maven.firstdarkdev.xyz/snapshots")
        .mavenUrlSimple("https://www.jitpack.io")
        .mavenUrlSimple("https://maven.shedaniel.me")
        .mavenUrlSimple("https://maven.crystaelix.com/releases/", "thelm.packagedmekemicals",
            "thelm.packagedauto", "thelm.packagedexcrafting")
        .mavenUrlSimple("https://jitpack.io", "com.github.rtyley")
        .mavenUrlSimple("https://beta.cursemaven.com", "curse.maven")
        .mavenUrlSimple("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/", "software.bernie.geckolib")
        .mavenUrlSimple("https://maven.neoforged.net/releases")
        .mavenUrlSimple("https://maven.parchmentmc.org")
        .mavenUrlSimple("https://maven.dragons.plus/releases")
        .mavenUrlSimple("https://maven.createmod.net", "dev.engine-room.flywheel",
            "dev.engine-room.vanillin",
            "com.simibubi.create", "net.createmod.ponder") // Ponder, Flywheel
        .mavenUrlSimple("https://maven.ithundxr.dev/snapshots")
        .mavenUrlSimple("https://mvn.devos.one/snapshots") // Registrate
        .mavenUrlSimple("https://maven.blamejared.com") // Jei
        .mavenUrlSimple("https://maven.theillusivec4.top/") // Curios
        .mavenUrlSimple("https://api.modrinth.com/maven", "maven.modrinth")
        .mavenUrlSimple("https://raw.githubusercontent.com/Fuzss/modresources/main/maven")
        // NeoForge config api port, needed by ponder
        .mavenUrlSimple("https://maven.fallenbreath.me/releases") // Conditional Mixin
        .mavenUrlSimple("https://raw.github.com/0999312/MMMaven/main/repository")
        .mavenUrlSimple("https://modmaven.dev/artifactory/local-releases/")
        .mavenUrlSimple("https://maven.blakesmods.com/releases")
        .mavenUrlSimple("https://modmaven.dev/", "com.alexthw.ars_elemental")
        .mavenUrlSimple("https://maven.architectury.dev/")
        .mavenUrlSimple("https://maven.latvian.dev/releases", "dev.latvian.mods",
            "dev.latvian.apps")
        .mavenUrlSimple("https://thedarkcolour.github.io/KotlinForForge/")
        .libs(rootProject, "libs")

}

fun RepositoryHandler.mavenUrlSimple(
    url: String,
    vararg includeGroup: String
): RepositoryHandler {
    return mavenUrl(url = url, includeGroup = includeGroup)
}

fun RepositoryHandler.mavenUrl(
    url: String,
    mavenName: String = "",
    excludeGroup: Array<String> = emptyArray(),
    vararg includeGroup: String
): RepositoryHandler {
    maven(url) {
        if (mavenName.isNotEmpty()) name = mavenName
        if (includeGroup.isEmpty() && excludeGroup.isEmpty()) return@maven
        content {
            if (includeGroup.isNotEmpty())
                includeGroup.forEach {
                    includeGroup(it)
                }
            if (excludeGroup.isNotEmpty())
                excludeGroup.forEach {
                    excludeGroup(it)
                }
        }
    }
    return this
}

fun RepositoryHandler.libs(project: Project, dir: String): RepositoryHandler {
    val libs = project.file(dir)
    libs.mkdirs()
    this.flatDir {
        dir(libs)
    }
    return this
}


fun RepositoryHandler.local(): RepositoryHandler {
    this.mavenLocal()
    return this
}
fun RepositoryHandler.central(): RepositoryHandler {
    this.mavenCentral()
    return this
}