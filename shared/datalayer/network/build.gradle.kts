plugins {
    kotlin("native.cocoapods")
    kotlin("multiplatform")
    id("com.android.library")
    id("kotlinx-serialization")
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
            baseName = "datalayer.network"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                with(Ktor) {
                    implementation(client_core)
                    implementation(client_cio)
                    implementation(client_loggin)
                    implementation(client_json)
                    implementation(content_negotiation)
                }

                implementation(JetBrains.kotlin_reflect)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting
        val androidUnitTest by getting
        val iosSimulatorArm64Main by getting

        val iosMain by getting {
            dependsOn(commonMain)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosSimulatorArm64Test by getting
        val iosTest by getting {
            dependsOn(commonTest)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    compileSdk = Versions.compile_sdk

    namespace = "com.tinyspace.datalayer.network"


    defaultConfig {
        minSdk = Versions.min_sdk
        manifestPlaceholders["appAuthRedirectScheme"] = "empty"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}