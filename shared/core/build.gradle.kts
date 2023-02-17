plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("native.cocoapods")
}

kotlin {
    android()
    ios()
    iosSimulatorArm64()


    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "datalayer.local"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(JetBrains.kotlin_coroutine)
                implementation(JetBrains.serialization)
                implementation(JetBrains.kotlin_time)

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
            }
        }
        val androidUnitTest by getting

        val iosMain by getting {
            dependsOn(commonMain)
        }
        val iosTest by getting

//        val iosSimulatorArm64Test by getting
//        val iosSimulatorArm64 by getting
    }

}

android {
    compileSdk = Versions.compile_sdk

    namespace = "com.tinyspace.shared.core"


    defaultConfig {
        minSdk = Versions.min_sdk
        manifestPlaceholders["appAuthRedirectScheme"] = "empty"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}



