plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("7.4.0").apply(false)
    id("com.android.library").version("7.4.0").apply(false)
    kotlin("android").version("1.8.0").apply(false)
    kotlin("multiplatform").version("1.8.0").apply(false)
    id("com.chromaticnoise.multiplatform-swiftpackage") version "2.0.3"
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