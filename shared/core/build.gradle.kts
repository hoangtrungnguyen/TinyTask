plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin{
    android()
    ios()
}

android {
    compileSdk = Versions.compile_sdk

    namespace = "com.tinyspace.core"


    defaultConfig {
        minSdk = Versions.min_sdk
        manifestPlaceholders["appAuthRedirectScheme"] = "empty"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
