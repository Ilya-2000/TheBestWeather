package com.impact.thebestweather.models

import com.impact.thebestweather.utils.Constant

data class WeatherRequest (
    val lat: Double,
    val lon: Double,
    val exclude: String,
    val appid: String
        )
