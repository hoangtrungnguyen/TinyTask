# How to use this project

## Must have library in every android modules

```kotlin
// build.gradle.kts

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "${change package name}"
    compileSdk = Versions.compile_sdk

    defaultConfig {
        minSdk = Versions.min_sdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose_complier
    }
}

dependencies {
    //common android feature's dependencies
    implementation(project(":android:core:compose"))
    implementation(project(":android:core:common"))
    implementation(project(":shared:domain"))

    with(AndroidTest) {
        androidTestImplementation(androidx)
        androidTestImplementation(espresso)

    }

    testImplementation(Junit.junit4)
    
    
    //dependencies for android feature
    with(Compose){
        implementation(material_icons)
        implementation(material3)
        implementation(material3_window)
        implementation(ui)
        implementation(ui_tooling)
        implementation(ui_tooling_preview)
        implementation(foundation)
        implementation(activity)
    }

    with(ViewModel) {
        implementation(life_cycle)
    }

    with(Koin) {
        implementation(koin_core)
        implementation(koin_compose)
        implementation(koin_android)
    }

    with(JetBrains) {
        implementation(kotlin_time)
        implementation(kotlin_coroutine)
        implementation(serialization)
    }
    implementation(Deps.coil)

    implementation(JetBrains.kotlin_coroutine)

}

```
