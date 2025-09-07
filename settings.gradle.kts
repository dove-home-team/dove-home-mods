pluginManagement {
    repositories {
        gradlePluginPortal()
        maven {
            url = uri("https://maven.firstdark.dev/releases")
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}

var projects = mapOf(
    "lib" to "Lib",
    "act" to "Act",
    "pack" to "Mod Pack",
    "botania" to "Botania",
)
projects.forEach { (id, name) ->
     val replaceProperties = mapOf(
         "\${id}" to id,
         "\${sid}" to id.replace("_", ""),
         "\${name}" to name,
         "\${pack}" to "io.github.dovehometeam.dove${id.replace("_", "")}",
         "\${pack_path}" to "io/github/dovehometeam/dove${id.replace("_", "")}",
     )

     resourceProjectGen(file("src/${id}/resources"), replaceProperties)
     codeProjectGen(file("src/${id}/java"), replaceProperties)
     val moduleDir = file("gradle/modules/${id}")
     moduleProjectGen(moduleDir, replaceProperties)

     include(id)
     project(":$id").projectDir = moduleDir
 }

file("crowdin.yml").bufferedWriter(Charsets.UTF_8).use { out ->
    out.write("""
        project_id_env: DOVE_CROWDIN_PROJECT_ID
        api_token_env: DOVE_CROWDIN_API_TOKEN
        preserve_hierarchy: true
        files:
    """.trimIndent())
    out.newLine()

    projects.forEach { (id, _) ->
        val path = "src/${id}/generated/assets/dove_${id}/lang/en_us.json"
        if (file(path).exists()) {
            out.write("  - source: /${path}")
            out.newLine()
            out.write("    dest: /dove-${id}.en-us.json")
            out.newLine()
            out.write("    translation: /src/${id}/resources/assets/dove_${id}/lang/%locale_with_underscore%.json")
            out.newLine()
        }
    }
}

fun moduleProjectGen(
    moduleDir: File,
    replaceProperties: Map<String, String> = emptyMap()
) {
    if (moduleDir.exists()) return
    moduleDir.mkdirs()
    val tempModuleDir = file("tmp-module/gradle/modules/\${id}")
    tempModuleDir
        .walk()
        .sortedBy { !it.isDirectory }
        .forEach {
            var relativeTo = it.relativeTo(tempModuleDir).path

            replaceProperties.forEach { k, v ->
                relativeTo = relativeTo.replace(k, v)
            }
            val target = moduleDir.resolve(relativeTo
            )
            if (it.isDirectory) {
                target.mkdirs()
            } else {
                val copyTo = it.copyTo(target)
                var replace = copyTo.readText(Charsets.UTF_8)
                replaceProperties.forEach { k, v ->
                    replace = replace.replace(k, v)
                }
                copyTo.bufferedWriter(Charsets.UTF_8).use {
                    it.write(replace)
                }
            }
        }
}

fun resourceProjectGen(
    resourceDir: File,
    replaceProperties: Map<String, String> = emptyMap()
) {
    if (resourceDir.exists()) return
    resourceDir.mkdirs()
    val tempResourceDir = file("tmp-module/src/\${id}/resources")
    tempResourceDir
        .walk()
        .sortedBy { !it.isDirectory }
        .forEach {
            var relativeTo = it.relativeTo(tempResourceDir).path//提取出相对文件
            replaceProperties.forEach { k, v ->
                relativeTo = relativeTo.replace(k, v)
            }
            val target = resourceDir.resolve(relativeTo)
            if (it.isDirectory) {
                target.mkdirs()
            } else {
                val copyTo = it.copyTo(target)
                var replace = copyTo.readText(Charsets.UTF_8)
                replaceProperties.forEach { k, v ->
                    replace = replace.replace(k, v)
                }
                copyTo.bufferedWriter(Charsets.UTF_8).use {
                    it.write(replace)
                }
            }
        }
}

fun codeProjectGen(codeDir: File, replaceProperties: Map<String, String> = emptyMap()) {
    if (codeDir.exists()) return
    codeDir.mkdirs()
    val tempCodeDir = file("tmp-module/src/\${id}/java")
    tempCodeDir
        .walk()
        .sortedBy { !it.isDirectory }
        .forEach {
            var relativeTo = it.relativeTo(tempCodeDir).path
            replaceProperties.forEach { k, v ->
                relativeTo = relativeTo.replace(k, v)
            }
            val target = codeDir.resolve(relativeTo)
            if (it.isDirectory) {
                target.mkdirs()
            } else {
                val copyTo = it.copyTo(target)
                var replace = copyTo.readText(Charsets.UTF_8)
                replaceProperties.forEach { k, v ->
                    replace = replace.replace(k, v)
                }
                copyTo.bufferedWriter(Charsets.UTF_8).use {
                    it.write(replace)
                }
            }
        }
}