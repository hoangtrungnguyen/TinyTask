plugins {
    id("com.android.library")
    kotlin("android")
}


android {
    namespace = "com.tinyspace.compose"
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

    with(Compose){
        implementation(Compose.material3)
        implementation(Compose.material3_window)
        implementation(Compose.foundation)
    }

}