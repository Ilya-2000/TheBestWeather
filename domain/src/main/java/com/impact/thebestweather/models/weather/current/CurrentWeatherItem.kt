package com.impact.thebestweather.models.weather.current

data class CurrentWeatherItem(
    val EpochTime: Int,
    val HasPrecipitation: Boolean,
    val IsDayTime: Boolean,
    val Link: String,
    val LocalObservationDateTime: String,
    val MobileLink: String,
    val PrecipitationType: Any,
    val Temperature: Temperature,
    val WeatherIcon: Int,
    val WeatherText: String
)