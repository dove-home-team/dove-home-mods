plugins {
    id("io.freefair.lombok").version("8.12")
}

dependencies {
    implementation(project(":java-annotation"))
//    compileOnly("org.projectlombok:lombok:1.18.38")
//    annotationProcessor("org.projectlombok:lombok:1.18.38")
    compileOnly("com.google.auto.service:auto-service:1.1.1")
    annotationProcessor("com.google.auto.service:auto-service:1.1.1")
}

tasks.withType<JavaCompile>().configureEach {
    sourceCompatibility = JavaVersion.VERSION_21.toString()
    targetCompatibility = sourceCompatibility
    options.encoding = "UTF-8" // Use the UTF-8 charset for Java compilation
    options.compilerArgs = listOf(
        "--add-exports", "jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED",
        "--add-exports", "jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED",
        "--add-exports", "jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED",
        "--add-exports", "jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED",
        "--add-exports", "jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED",
        "--add-exports", "jdk.compiler/com.sun.tools.javac.model=ALL-UNNAMED",
        "--add-exports", "jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED",
        "--add-exports", "jdk.compiler/com.sun.tools.javac.processing=ALL-UNNAMED",
    )
}