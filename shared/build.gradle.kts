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


    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }


    sourceSets {
        val commonMain by getting {
            dependencies {
//                api("io.insert-koin:koin-core:3.1.2")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0-RC")
                implementation(Koin.koin_core)
                implementation(fileTree("domain"))
            }
        }


        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")

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

        val androidMain by getting
        val androidUnitTest by getting
        val iosX64Main by getting {
            dependencies {

            }
        }
        val iosArm64Main by getting {
            dependencies{

            }
        }
        val iosSimulatorArm64Main by getting {
            dependencies{
            }
        }


        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
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


