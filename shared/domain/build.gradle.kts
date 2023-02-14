plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
}

kotlin{
    android()
    ios()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "domain"
        }
    }

    sourceSets{
        val commonMain by getting {
            dependencies {
            }
        }
    }
}

android {
    compileSdk = Versions.compile_sdk

    namespace = "com.tinyspace.domain"

    defaultConfig {
        minSdk = Versions.min_sdk
        manifestPlaceholders["appAuthRedirectScheme"] = "empty"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }


}



dependencies {
//    implementation(project(":shared:datalayer:repository"))
    implementation(project(mapOf("path" to ":shared:datalayer:repository")))
//    implementation(project(":shared:datalayer:local"))
//    implementation(project(":shared:datalayer:network"))
//    commonMainImplementation(project(":shared:core:database"))
////    commonMainImplementation(project(Koin.koin_core))
//
//    commonMainImplementation(libs.koin)
//    commonMainImplementation(libs.squareup.sqldelight.runtime)
//
//    androidMainImplementation(libs.squareup.sqldelight.driver.android)
//    iosMainImplementation(libs.koin)
//    iosMainImplementation(libs.squareup.sqldelight.driver.native)
}