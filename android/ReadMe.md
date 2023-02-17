# How to use this project

## Must have library in every android modules

```kotlin
dependencies {
    implementation(project(":android:core:compose"))
    implementation(project(":android:core:common"))
    implementation(project(":shared:domain"))

    with(AndroidTest) {
        androidTestImplementation(androidx)
        androidTestImplementation(espresso)

    }

    testImplementation(Junit.junit4)
}

```
