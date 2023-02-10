plugins {
    //trick: for the same plugin versions in all sub-modules
    kotlin("multiplatform").version("1.8.0").apply(false)
    id("com.chromaticnoise.multiplatform-swiftpackage") version "2.0.3"
    id("org.jetbrains.kotlin.jvm") version "1.7.21" apply false
    id("com.android.library") version "7.4.0" apply false
    id("org.jetbrains.kotlin.android") version "1.7.21" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

val koinVersion by extra("3.3.0")

buildscript {

}

//sqldelight {
//    database("AppDatabase") {
//        packageName = "com.tinyspace.task"
//    }
//}