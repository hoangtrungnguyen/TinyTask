import com.moandjiezana.toml.Toml
import java.io.File


const val cocopods = "native.cocoapods"

object Libs {
    //Not use yet
    private val toml = Toml().read(File("libs.toml"))
}
object Versions {


    const val rushwolf: String = "1.0.0"
    const val view_model: String = "2.5.1"
    const val espresso: String = "3.5.1"
    const val androidx: String = "1.1.5"
    const val junit4 = "4.13.2"

    // 1
    const val min_sdk = 30
    const val target_sdk = 33
    const val compile_sdk = 33

    const val compose_version = "1.3.1"
    const val material3 = "1.0.1"
    const val compose_navigation = "2.5.3"


    const val koin = "3.3.0"

    const val compose_activity = "1.6.1"

    const val compose_complier = "1.4.0"

    const val sqlDelightVersion = "2.0.0-alpha05"
}

class Dependencies

object Deps {


    const val coil = "io.coil-kt:coil-compose:2.2.2"
}


object Compose{
    const val material_icons = "androidx.compose.material:material-icons-extended:${Versions.compose_version}"
    const val navigation = "androidx.navigation:navigation-compose:${Versions.compose_version}"

    const val material3 = "androidx.compose.material3:material3:${Versions.material3}"
    const val material3_window = "androidx.compose.material3:material3-window-size-class:${Versions.material3}"

    const val ui = "androidx.compose.ui:ui:${Versions.compose_version}"
    const val ui_tooling = "androidx.compose.ui:ui-tooling:${Versions.compose_version}"
    const val ui_tooling_preview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose_version}"
    const val foundation = "androidx.compose.foundation:foundation:${Versions.compose_version}"

    const val activity = "androidx.activity:activity-compose:${Versions.compose_activity}"

}

object ViewModel {
    const val life_cycle = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.view_model}"
}


object Ads {
    const val admob = "com.google.android.gms:play-services-ads:21.5.0"
}

object AndroidTest {

    const val androidx = "androidx.test.ext:junit:${Versions.androidx}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}

object Junit {
    const val junit4 = "junit:junit:${Versions.junit4}"
}
object Coroutine

object SQLDelight {
    const val slq_delight_android =  "app.cash.sqldelight:android-driver:${Versions.sqlDelightVersion}"
    const val slq_delight_native =  "app.cash.sqldelight:native-driver:${Versions.sqlDelightVersion}"
    const val sql_delight = "app.cash.sqldelight:sqlite-driver:${Versions.sqlDelightVersion}"
    const val sql_coroutine = "app.cash.sqldelight:coroutines-extensions:${Versions.sqlDelightVersion}"
}

object Koin{
    const val koin_compose = "io.insert-koin:koin-androidx-compose:${Versions.koin}"
    const val koin_android = "io.insert-koin:koin-android:${Versions.koin}"
    const val koin_core = "io.insert-koin:koin-core:${Versions.koin}"
    const val koin_test = "io.insert-koin:koin-test:${Versions.koin}"
}


object Rushwolf{
    const val settings = "com.russhwolf:multiplatform-settings:${Versions.rushwolf}"
}

object UUID {
    const val benasher44 = "com.benasher44:uuid:0.6.0"
}

object JetBrains {
    val kotlin_coroutine_test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4"
    const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0-RC"
    const val kotlin_coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4"
    const val kotlin_coroutine_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4"
    const val kotlin_time =   "org.jetbrains.kotlinx:kotlinx-datetime:0.4.0"
}