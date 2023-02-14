import com.moandjiezana.toml.Toml
import java.io.File


const val cocopods = "native.cocoapods"

object Libs {
    private val toml = Toml().read(File("libs.toml"))

//    val retrofit = toml.getTable("retrofit").getString("version")
//    val okHttp = toml.getTable("okhttp").getString("version")
    // Add more libraries here
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

class Dependencies {
}

object Deps {


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


object Test {

    const val junit4 = "junit:junit:${Versions.junit4}"
    const val androidx = "androidx.test.ext:junit:${Versions.androidx}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}
object Coroutine{

}

object SQLDelight {
    const val slq_delight_android =  "app.cash.sqldelight:android-driver:${Versions.sqlDelightVersion}"
    const val slq_delight_native =  "app.cash.sqldelight:native-driver:${Versions.sqlDelightVersion}"
    const val sql_delight = "app.cash.sqldelight:sqlite-driver:${Versions.sqlDelightVersion}"
}

object Koin{
    const val koin_compose = "io.insert-koin:koin-androidx-compose:${Versions.koin}"
    const val koin_core = "io.insert-koin:koin-core:${Versions.koin}"
    const val koin_test = "io.insert-koin:koin-test:${Versions.koin}"
}


object SharedPref{
    const val rushwolf = "com.russhwolf:multiplatform-settings:${Versions.rushwolf}"
}