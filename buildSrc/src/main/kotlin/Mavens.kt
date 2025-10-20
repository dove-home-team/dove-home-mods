import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven

/**
 * @author baka4n
 * @code @Date 2025/9/18 15:30:41
 */
fun RepositoryHandler.mavenSettings(project: Project) {

    mavenLocal()
    mavenCentral()
    maven("https://maven.minecraftforge.net/") {
        content {
            includeGroup("com.github.glitchfiend")
        }
    }
    maven("https://maven.firstdarkdev.xyz/snapshots")
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
    maven("http://maven.dragons.plus/releases") {
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
        dir(project.rootProject.file("libs"))
    }
}