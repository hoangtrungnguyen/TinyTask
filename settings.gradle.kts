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

include(
    ":android:androidApp",
":android:features:taskForm",
":android:features:counter",
":android:core:compose"

)


include(
    ":shared",
    ":shared:datalayer:repository",
    ":shared:domain",
    ":shared:core",
    ":shared:datalayer:local",
    ":shared:datalayer:network"
)
