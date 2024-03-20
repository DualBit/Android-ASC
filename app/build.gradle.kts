plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    compileSdk = Sdk.compile_sdk_version
    namespace = AppConfiguration.application_id

    defaultConfig {
        applicationId = AppConfiguration.application_id
        minSdk = Sdk.min_sdk_version
        targetSdk = Sdk.target_sdk_version
        versionCode = AppConfiguration.version_code
        versionName = AppConfiguration.version_name

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFile(getDefaultProguardFile("proguard-android-optimize.txt"))
        }
    }

    flavorDimensions.add("version")

    productFlavors {
        create("dev") {
            dimension = "version"
            versionNameSuffix = "-dev"
            applicationId = AppConfiguration.application_id
            buildConfigField("String", "BASE_URL", "\"https://startup-compose-example/api/\"")
            resValue("string",  "app_name", "DEV-${AppConfiguration.application_name}")
        }

        create("stag") {
            dimension = "version"
            versionNameSuffix = "-stag"
            applicationId = AppConfiguration.application_id
            buildConfigField("String", "BASE_URL", "\"https://startup-compose-example/api/\"")
            resValue("string",  "app_name", "STAG-${AppConfiguration.application_name}")
        }

        create("prod") {
            dimension = "version"
            applicationId = AppConfiguration.application_id
            buildConfigField("String", "BASE_URL", "\"https://startup-compose-example/api/\"")
            resValue("string",  "app_name", AppConfiguration.application_name)
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose_compiler
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(CoreLibs.core_ktx)
    implementation(CoreLibs.appcompat)
    implementation(CoreLibs.activity_compose)
    implementation(CoreLibs.material)

    implementation(LifecycleLibs.runtime_ktx)
    implementation(LifecycleLibs.viewmodel_ktx)
    implementation(LifecycleLibs.runtime_compose)

    implementation(NavigationLibs.navigation_compose)

    implementation(AccompanistLibs.insets)
    implementation(AccompanistLibs.systemuicontroller)

    implementation(CoroutinesLibs.core)
    implementation(CoroutinesLibs.play_services)

    implementation(HiltLibs.android)
    implementation(HiltLibs.navigation_compose)
    ksp(HiltLibs.compiler)
    ksp(HiltLibs.android_compiler)

    implementation(RetrofitLibs.retrofit)
    implementation(RetrofitLibs.converter_gson)
    implementation(RetrofitLibs.logging_interceptor)

    implementation(platform(ComposeLibs.bom))
    androidTestImplementation(platform(ComposeLibs.bom))

    implementation(ComposeLibs.material3)
    implementation(ComposeLibs.material3_window_size)

    implementation(ComposeLibs.animation)
    implementation(ComposeLibs.compiler)
    implementation(ComposeLibs.foundation)
    implementation(ComposeLibs.material)
    implementation(ComposeLibs.runtime)
    implementation(ComposeLibs.ui)

    implementation(platform(FirebaseLibs.bom))

    implementation(DataStoreLibs.datastore_preferences)

    implementation(SplashScreen.splashscreen)

    implementation(Coil.coil)

    debugImplementation(ComposeLibs.ui_tooling)
    debugImplementation(ComposeLibs.ui_tooling_preview)

    implementation(GeoLocation.location)
    implementation(GeoLocation.accompaints_permission)

    testImplementation(TestLibs.junit)
    androidTestImplementation(AndroidXTestLibs.junit)
    androidTestImplementation(ComposeLibs.ui_test_junit4)
}