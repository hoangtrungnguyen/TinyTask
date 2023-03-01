plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    mavenCentral()
    gradlePluginPortal()

}

dependencies {
    implementation("com.moandjiezana.toml:toml4j:0.7.2")
}