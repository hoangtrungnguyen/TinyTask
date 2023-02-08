package com.tinyspace.tinytask.domain

/**
 * @duration : second
 */
data class Counter(
    val duration: Int,
    val progress: Float
){
    fun toTimer() : String {
        return "${duration}"
    }

    fun isWarmUp(): Boolean = progress < 0.15

    fun isMain() : Boolean = progress in 0.15..0.85

    fun isCoolDown() : Boolean = progress > 0.85
}


