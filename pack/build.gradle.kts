dependencies {
    implementation(project(":lib"))
    implementation(":botania-neoforge-1.21.1-451-SNAPSHOT@jar")
    implementation("mekanism:Mekanism:${libs.versions.mekanism.version.get()}:additions")
    implementation("mekanism:Mekanism:${libs.versions.mekanism.version.get()}:tools")
    implementation("mekanism:Mekanism:${libs.versions.mekanism.version.get()}:generators")

}