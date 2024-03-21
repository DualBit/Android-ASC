apply("../buildsystem/activity.gradle")
plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}
android {
    namespace = "com.amity.socialcloud.uikit.community"
    buildFeatures {
        viewBinding = true
        dataBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.5"
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
    val amityMessagingSdkVersion: String by rootProject.extra
    implementation(project(":common"))
    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.appcompat:appcompat:1.3.0")

    implementation("androidx.lifecycle:lifecycle-reactivestreams-ktx:2.4.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("androidx.recyclerview:recyclerview:1.2.0")

    implementation("androidx.activity:activity-compose:1.6.0")

    api("com.linkedin.android.spyglass:spyglass:3.0.1")
    api("com.github.AmityCo.Amity-Social-Cloud-SDK-Android:amity-video-publisher:$amityMessagingSdkVersion")
    api("com.github.AmityCo.Amity-Social-Cloud-SDK-Android:amity-video-player:$amityMessagingSdkVersion")
    implementation(project(":social-compose"))
}
