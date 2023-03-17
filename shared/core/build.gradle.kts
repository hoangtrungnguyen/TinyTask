plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin(cocopods)
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
            baseName = "core"
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
                implementation("androidx.startup:startup-runtime:1.1.1")
            }
        }
        val androidUnitTest by getting

        val iosSimulatorArm64Main by getting

        val iosMain by getting {
            dependsOn(commonMain)
            dependencies {
            }
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosTest by getting

    }

}

android {
    compileSdk = Versions.compile_sdk

    namespace = "com.tinyspace.shared.core"


    defaultConfig {
        minSdk = Versions.min_sdk
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}



