plugins {
    kotlin("multiplatform")
    kotlin(cocopods)
    id("com.android.library")
    id("com.chromaticnoise.multiplatform-swiftpackage") version "2.0.3"
    id("app.cash.sqldelight")
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    ios {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }


    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
//                api("io.insert-koin:koin-core:3.1.2")
                implementation(JetBrains.kotlin_coroutine)
                implementation(JetBrains.serialization)
                implementation(JetBrains.kotlin_time)

                implementation(project(":shared:domain"))
                implementation(project(":shared:datalayer:local"))
                implementation(project(":shared:datalayer:repository"))
                implementation(project(":shared:core"))
                implementation(Rushwolf.settings)
                implementation(Koin.koin_core)
            }
        }


        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(JetBrains.kotlin_coroutine_test)
                implementation(Koin.koin_test)
            }
        }


        cocoapods {
            summary = "Some description for the Shared Module"
            homepage = "Link to the Shared Module homepage"
            ios.deploymentTarget = "14.1"
            podfile = project.file("../iosApp/Podfile")
            framework {
                baseName = "shared"
            }
            version = "1.11.3"
        }

        val androidMain by getting{
            dependencies {
                implementation(SQLDelight.slq_delight_android)
                implementation(Koin.koin_android)
            }
        }
        val androidUnitTest by getting

        val iosSimulatorArm64Main by getting

        val iosMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(SQLDelight.slq_delight_native)
            }
            iosSimulatorArm64Main.dependsOn(this)
        }

        val iosSimulatorArm64Test by getting
        val iosTest by getting {}
    }
}

android {
    namespace = "com.tinyspace.tinytask.android"
    compileSdk = Versions.compile_sdk
    defaultConfig {
        minSdk = Versions.min_sdk
    }
}

multiplatformSwiftPackage {
    swiftToolsVersion("5.3")
    targetPlatforms {
        iOS { v("14.1") }
    }
    packageName("shared")
}

dependencies {
//    commonMainApi(project(":shared:core"))
}