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
    ":shared:core",
    ":shared:datalayer:local",
    ":shared:datalayer:network" ,
    ":shared:datalayer:repository",
    ":shared:domain",
    ":shared",
)


include(
    ":android:androidApp",
    ":android:features:taskForm",
    ":android:features:counter",
    ":android:core:compose"
)


include(":shared:common")
include(":shared:datalayer:model")
include(":android:features:todolist")
include(":android:features:taskHistory")
include(":android:core:common")
