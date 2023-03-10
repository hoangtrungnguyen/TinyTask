package com.tinyspace.taskform

import android.content.SharedPreferences
import com.tinyspace.common.SharedPref
import kotlinx.datetime.LocalDate

fun SharedPreferences.setHighlight(date: LocalDate) {
    with(edit()) {
        putString(SharedPref.HIGHLIGHT, "")
        apply()
    }
}

fun SharedPref.isHighlight(): Boolean {
//    LocalDate.fromEpochDays()
    return true
}