apply("../buildsystem/activity.gradle")
plugins {
    id("com.android.library")
    id("kotlin-android")
}
dependencies {
    android {
        namespace = "com.amity.socialcloud.uikit.chat"
    }
    implementation(project(":common"))
}
