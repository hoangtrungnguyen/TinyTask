plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
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
            baseName = "datalayer.repository"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Koin.koin_core)
                implementation(JetBrains.kotlin_coroutine)
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
        val iosMain by getting  {
            dependsOn(commonMain)
            iosSimulatorArm64Main.dependsOn(this)
        }

        val iosTest by getting
    }

}
android {
    compileSdk = Versions.compile_sdk

    namespace = "com.tinyspace.datalayer.repository"


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
    commonMainImplementation(project(":shared:datalayer:local"))
    commonMainImplementation(project(":shared:datalayer:network"))

}