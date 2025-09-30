
dependencies {
    compileOnly(project(":lib"))
    localRuntime(project(":lib"))
    localRuntime(project(":act"))
    localRuntime(project(":botania"))
    localRuntime(project(":nucleoplasm"))
    localRuntime("dev.architectury:architectury-neoforge:13.0.8")
    implementation("com.tterrag.registrate:Registrate:MC1.21-1.3.0+62")
    modImplementation("net.createmod.ponder:Ponder-NeoForge-1.21.1:1.0.56")
    compileOnly("dev.engine-room.flywheel:flywheel-neoforge-api-1.21.1:1.0.4-30")
    compileOnly("dev.engine-room.vanillin:vanillin-neoforge-1.21.1:1.1.3-30")
    localRuntime("dev.engine-room.vanillin:vanillin-neoforge-1.21.1:1.1.3-30")
    localRuntime("dev.engine-room.flywheel:flywheel-neoforge-1.21.1:1.0.4-30")
    modImplementation("com.simibubi.create:create-1.21.1:6.0.7-117:slim") {
        isTransitive = false
    }
    modImplementation("top.theillusivec4.curios:curios-neoforge:9.5.1+1.21.1")

//    implementation(":botania-neoforge-1.21.1-451-SNAPSHOT@jar")
}