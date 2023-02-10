plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.tinyspace.tinytask.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.tinyspace.tinytask.android"
        minSdk = 30
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
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
    implementation("androidx.compose.ui:ui:${Versions.compose_version}")
    implementation("androidx.compose.ui:ui-tooling:${Versions.compose_version}")
    implementation("androidx.compose.ui:ui-tooling-preview:${Versions.compose_version}")
    implementation("androidx.compose.foundation:foundation:${Versions.compose_version}")
    implementation("androidx.activity:activity-compose:1.6.1")

    implementation("io.insert-koin:koin-android:${rootProject.ext["koinVersion"]}")
    implementation("io.insert-koin:koin-androidx-compose:${rootProject.ext["koinVersion"]}")

    with(Compose){
        implementation(material_icons)
        implementation(navigation)
        implementation(Compose.material3)
        implementation(Compose.material3_window)
    }

}