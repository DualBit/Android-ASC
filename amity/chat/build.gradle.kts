apply("../buildsystem/activity.gradle")
plugins {
    id("com.android.library")
    id("kotlin-android")
}
android {
    namespace = "com.amity.socialcloud.uikit.chat"
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/INDEX.LIST"
            excludes += "META-INF/io.netty.versions.properties"
        }
    }
}
dependencies {
    implementation(project(":common"))
}
