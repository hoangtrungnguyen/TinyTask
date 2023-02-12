plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "com.tinyspace.taskform"
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

    implementation(project(":android:core:compose"))

    with(Test){
        androidTestImplementation(Test.androidx)
        androidTestImplementation(Test.espresso)
        testImplementation(Test.junit4)
    }

    with(Compose){
        implementation(material_icons)
        implementation(Compose.material3)
        implementation(Compose.material3_window)
        implementation(ui)
        implementation(Compose.ui_tooling)
        implementation(ui_tooling_preview)
        implementation(Compose.foundation)
        implementation(Compose.activity)
    }

    with(ViewModel){
        implementation(ViewModel.life_cycle)
    }
}