import org.gradle.internal.impldep.org.eclipse.jgit.util.FileUtils.rename

/**
 * A plugin to cleanup the template after it has been forked. It register a single `templateCleanup`
 * task that is designed to run from CI. It:
 * - renames the root project
 * - replaces the maven coordinates with coordinates based on the Github repository where the
 * template is forked
 * - changes the package name
 * - changes the Android application ID
 * - cleanups after itself by removing the Github action and this plugin
 */

tasks.register("templateCleanup") {
    doLast {
        val (appName, project, packageName) = listOf(
            "ReplaceAppName",
            "replace_name_project",
            "com.replace.package.project"
        )
        file("settings.gradle.kts").replace(
            "StartupComposeTemplate",
            project
        )
        renamingAppConfiguration(appName, packageName)
        renamingComposableFile(appName)
        patchReadme(appName)
        changePackageName(packageName)

        // cleanup the cleanup :)
        file("build.gradle.kts").replace(
            "    cleanup\n",
            ""
        )
        file("buildSrc/src/main/kotlin/cleanup.gradle.kts").delete()
        file("screenshots/").deleteRecursively()
    }
}

fun String.sanitized() = replace(Regex("[^A-Za-z0-9]"), "").toLowerCase()

fun File.replace(regex: Regex, replacement: String) {
    writeText(readText().replace(regex, replacement))
}

fun File.replace(oldValue: String, newValue: String) {
    writeText(readText().replace(oldValue, newValue))
}

fun patchReadme() {
    var featuresFound = false
    val existingReadme = file("README.md").readLines().mapNotNull {
        if (it.startsWith("## Features")) {
            featuresFound = true
        }
        if (!featuresFound) null else it
    }.joinToString("\n")

    file("README.md").writeText(existingReadme)
}

fun patchReadme(appName: String) {
    var readMeText = file("README_GEN.md").readText()
    readMeText = readMeText.replace("<<ReplaceAppName>>", appName)
    file("README.md").writeText(readMeText)
    file("README_GEN.md").delete()
}

fun srcDirectories() = projectDir.listFiles()!!
    .filter { it.isDirectory && !(it.name == "buildSrc") }
    .flatMap { it.listFiles()!!.filter { it.isDirectory && it.name == "src" } }

fun changePackageName(packageName: String) {
    srcDirectories().forEach {
        it.walk().filter {
            it.isFile && (it.extension == "kt" || it.extension == "kts" || it.extension == "xml")
        }.forEach {
            it.replace("com.startup.compose.template", packageName)
        }
    }
    val dirPackage = packageName.replace(".", "/")
    srcDirectories().forEach {
        it.listFiles()!!.filter { it.isDirectory } // down to src/main
            .flatMap { it.listFiles()!!.filter { it.isDirectory } } // down to src/main/java
            .forEach {
                val newDir = File(it, dirPackage)
                newDir.parentFile.mkdirs()
                File(it, "com/startup/compose/template").renameTo(newDir)
                File(it, "com/startup").deleteRecursively()
            }
    }
}

fun renamingComposableFile(appName: String) {
    file("app/src/main/java/com/startup/compose/template/ui/TemplateApp.kt").replace(
        "TemplateApp",
        "${appName}App"
    )
    file("app/src/main/java/com/startup/compose/template/MainActivity.kt").replace(
        "TemplateApp",
        "${appName}App"
    )
    file("app/src/main/java/com/startup/compose/template/MainActivity.kt").replace(
        "StartupComposeTemplateTheme",
        "${appName}Theme"
    )
    file("app/src/main/java/com/startup/compose/template/ui/theme/Theme.kt").replace(
        "StartupComposeTemplateTheme",
        "${appName}Theme"
    )
    file("app/src/main/java/com/startup/compose/template/ui/screen/home/HomeScreen.kt").replace(
        "StartupComposeTemplateTheme",
        "${appName}Theme"
    )
    srcDirectories().forEach {
        it.walk().first {
            it.name == "TemplateApp.kt" && it.extension == "kt"
        }.renameTo(file("app/src/main/java/com/startup/compose/template/ui/${appName}App.kt"))
    }
}

fun renamingAppConfiguration(appName: String, packageName: String) {
    file("buildSrc/src/main/java/AppConfiguration.kt").replace(
        "ComposeTemplate",
        appName
    )
    file("buildSrc/src/main/java/AppConfiguration.kt").replace(
        "com.startup.compose.template",
        packageName
    )
}
