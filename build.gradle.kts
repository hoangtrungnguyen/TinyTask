
plugins {
    //trick: for the same plugin versions in all sub-modules
    kotlin("multiplatform").version("1.8.0").apply(false)
    id("org.jetbrains.kotlin.jvm") version "1.7.21" apply false
    id("com.android.library") version "7.4.0" apply false
    id("org.jetbrains.kotlin.android") version "1.7.21" apply false
    id("app.cash.sqldelight") version "2.0.0-alpha05" apply false
    id("com.google.gms.google-services") version "4.3.15" apply false
    // Add the Google services Gradle plugin
}




tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}


