plugins {
    id("com.android.library")
}

apply("../buildsystem/activity.gradle")

android {
    namespace = "com.amity.socialcloud.uikit"
    buildTypes {
        release {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "amity-proguard-rules.pro"
            )
        }
        debug {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "amity-proguard-rules.pro"
            )
        }
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
    api(project(":social"))
    api(project(":chat"))
    api("com.github.AmityCo.Amity-Social-Cloud-SDK-Android:amity-sdk:$amityMessagingSdkVersion")
    implementation(project(":social-compose"))
}