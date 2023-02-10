
const val cocopods = "native.cocoapods"

object Versions {
    // 1
    const val min_sdk = 30
    const val target_sdk = 33
    const val compile_sdk = 33

    const val compose_version = "1.3.1"
    const val material3 = "1.0.1"
    const val compose_navigation = "2.5.3"


    const val koin = "3.3.0"

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
}

object Coroutine{

}

object Koin{
    const val koin_compose = "io.insert-koin:koin-androidx-compose:${Versions.koin}"
    const val koin_core = "io.insert-koin:koin-core:${Versions.koin}"
    const val koin_test = "io.insert-koin:koin-test:${Versions.koin}"
}