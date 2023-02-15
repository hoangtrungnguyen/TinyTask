package com.tinyspace.tinytask

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.tinyspace.datalayer.local.TaskDb
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule : Module = module {
    single<SqlDriver> {
        NativeSqliteDriver(TaskDb.Schema, "TaskDb")
    }
}