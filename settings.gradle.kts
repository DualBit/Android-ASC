pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "android-amity"
include(":app")

include(":social")
include(":amity-uikit")
include(":social-compose")
include(":chat")
include(":common")

project(":social").projectDir = File(rootDir, "amity/social/")
project(":amity-uikit").projectDir = File(rootDir, "amity/amity-uikit/")
project(":social-compose").projectDir = File(rootDir, "amity/social-compose/")
project(":chat").projectDir = File(rootDir, "amity/chat/")
project(":common").projectDir = File(rootDir, "amity/common/")
