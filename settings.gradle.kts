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
include(":android:androidApp")
include(":shared")
include(":shared:domain")

include(
    ":android:features:counter"
)

include(
    ":shared:datalayer:repository",
    ":shared:domain",
    ":shared:core",
    ":shared:datalayer:local",
    ":shared:datalayer:network"
)