plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.tinyspace.tinytask.android"
    compileSdk = Versions.compile_sdk
    defaultConfig {
        applicationId = "com.tinyspace.tinytask.android"
        minSdk = Versions.min_sdk
        targetSdk = Versions.target_sdk
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose_complier
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":shared:domain"))
    implementation(project(":android:features:counter"))
    implementation(project(":android:features:taskForm"))
    implementation(project(":android:core:compose"))



    with(Koin){
        implementation(Koin.koin_core)
        implementation(Koin.koin_compose)
    }

    with(Compose){
        implementation(material_icons)
        implementation(navigation)
        implementation(Compose.material3)
        implementation(Compose.material3_window)
        implementation(ui)
        implementation(Compose.ui_tooling)
        implementation(ui_tooling_preview)
        implementation(Compose.foundation)
        implementation(Compose.activity)
    }

}