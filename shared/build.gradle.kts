plugins {
    kotlin("multiplatform")
    kotlin(cocopods)
    id("com.android.library")
    id("com.chromaticnoise.multiplatform-swiftpackage") version "2.0.3"
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


    iosSimulatorArm64(){
            binaries.framework {
                baseName = "shared"
            }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
//                api("io.insert-koin:koin-core:3.1.2")
                implementation(JetBrains.kotlin_coroutine)
                implementation(JetBrains.serialization)
                implementation(JetBrains.kotlin_time)

                implementation(project(":shared:domain"))
                implementation(project(":shared:datalayer:repository"))
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

        val iosSimulatorArm64Main by getting {
            dependencies{
                implementation(Koin.koin_core)
                implementation(SQLDelight.slq_delight_native)
            }
        }

        val iosMain by getting {
            dependencies{
                implementation(Koin.koin_core)
                implementation(SQLDelight.slq_delight_native)
            }
        }

        val iosSimulatorArm64Test by getting
        val iosTest by getting {}
    }

//
//    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
//        binaries.withType<org.jetbrains.kotlin.gradle.plugin.mpp.Framework> {
//            isStatic = false
//            linkerOpts.add("-lsqlite3")
//
//            export(project(":shared:datalayer:local"))
//            export(project(":shared:datalayer:network"))
//            export(project(":shared:datalayer:repository"))
//            export(project(":shared:domain"))
//
//            embedBitcode(org.jetbrains.kotlin.gradle.plugin.mpp.Framework.BitcodeEmbeddingMode.BITCODE)
//
//            transitiveExport = true
//        }
//    }
}

android {
    namespace = "com.tinyspace.tinytask"
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
//    commonMainApi(project(":shared:domain"))
}