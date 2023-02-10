pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "TinyTask"
include(":androidApp")
include(":shared")
//include(":shared:core")
//include(":shared:localsource")

//include(":shared:network")
//include(":features")

include(
    ":shared:repository",
    ":shared:domain",
    ":shared:core",
    ":shared:localsource"
)