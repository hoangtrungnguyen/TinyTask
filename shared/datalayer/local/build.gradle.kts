plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("native.cocoapods")
    id("app.cash.sqldelight")
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
            baseName = "datalayer.local"
        }
    }

    sourceSets {
        val commonMain by getting{
            dependencies{
                implementation(Rushwolf.settings)
                implementation(JetBrains.kotlin_coroutine)
                implementation(SQLDelight.sql_coroutine)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies{
                implementation(SQLDelight.slq_delight_android)
            }
        }
        val androidUnitTest by getting

        val iosSimulatorArm64Test by getting

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

    namespace = "com.tinyspace.datalayer.local"


    defaultConfig {
        minSdk = Versions.min_sdk
        manifestPlaceholders["appAuthRedirectScheme"] = "empty"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}


sqldelight {
    databases {
        create("TaskDb") {
            packageName.set("com.tinyspace.datalayer.local")
            sourceFolders.set(listOf("sqldelight"))
            schemaOutputDirectory.set(file("src/main/sqldelight/databases"))
        }
    }
}