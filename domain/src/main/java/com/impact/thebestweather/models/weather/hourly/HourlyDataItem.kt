package com.impact.thebestweather.models.weather.hourly

data class HourlyDataItem(
    val DateTime: String,
    val EpochDateTime: Int,
    val HasPrecipitation: Boolean,
    val IconPhrase: String,
    val IsDaylight: Boolean,
    val Link: String,
    val MobileLink: String,
    val PrecipitationProbability: Int,
    val Temperature: Temperature,
    val WeatherIcon: Int
)