apply("../buildsystem/activity.gradle")
plugins {
    id("com.android.library")
}
android {
    namespace = "com.amity.socialcloud.uikit.common"
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}
dependencies {
    implementation("commons-io:commons-io:2.6")
    api("com.jakewharton.timber:timber:4.7.1")

    implementation("org.jsoup:jsoup:1.16.1")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
}
