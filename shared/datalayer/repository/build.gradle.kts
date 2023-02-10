plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}


dependencies {
    implementation(project(":shared:datalayer:local"))
    implementation(project(":shared:datalayer:network"))
    implementation(Koin.koin_core)
}