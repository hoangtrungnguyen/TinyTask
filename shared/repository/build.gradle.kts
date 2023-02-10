plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

//include(":shared:localsource")

dependencies {
//    implementation(project(":shared:localsource"))
    implementation(project(":shared:domain"))
}