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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/INDEX.LIST"
            excludes += "META-INF/io.netty.versions.properties"
        }
    }
}
dependencies {
    implementation("commons-io:commons-io:2.6")
    implementation("com.jakewharton.timber:timber:4.7.1")

    implementation("org.jsoup:jsoup:1.16.1")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
}
