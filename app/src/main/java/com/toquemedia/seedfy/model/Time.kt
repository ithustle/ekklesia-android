package com.toquemedia.seedfy.model

data class Time(val hour: Int, val minute: Int) {
    companion object {
        fun of(hour: Int, minute: Int): Time {
            return Time(hour, minute)
        }
    }
}