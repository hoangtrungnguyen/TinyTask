package com.tinyspace.tinytask

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.russhwolf.settings.Settings
import com.tinyspace.datalayer.local.TaskDb
import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.getOriginalKotlinClass
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults


actual val platformModule : Module = module {
    single<SqlDriver> {
        NativeSqliteDriver(TaskDb.Schema, "TaskDb")
    }
}

object KoinIOS {
    fun initialize(
        userDefaults: NSUserDefaults,
    ): KoinApplication = initKoin(
        koinAppDeclaration = module {

        }
    )
}

fun Koin.get(objCClass: ObjCClass): Any {
    val kClazz = getOriginalKotlinClass(objCClass)!!
    return get(kClazz, null, null)
}