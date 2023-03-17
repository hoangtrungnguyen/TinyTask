plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.tinyspace.android.stat"
    compileSdk = 33

    defaultConfig {
        minSdk = 30
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose_complier
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":android:core:compose"))
    implementation(project(":android:core:common"))
    implementation(project(":shared:domain"))

    with(AndroidTest) {
        androidTestImplementation(androidx)
        androidTestImplementation(espresso)
    }
    testImplementation(Junit.junit4)

    with(Compose) {
        implementation(material_icons)
        implementation(material3)
        implementation(material3_window)
        implementation(ui)
        implementation(ui_tooling)
        implementation(ui_tooling_preview)
        implementation(foundation)
        implementation(activity)
        implementation(runtim_compose)
    }

    with(Vico) {
        implementation(graph_compose)
        implementation(graph_core)
    }

    with(ViewModel) {
        implementation(life_cycle)
    }

    with(Koin) {
        implementation(koin_core)
        implementation(koin_compose)
    }

}