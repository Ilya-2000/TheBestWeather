package com.impact.thebestweather.models.weather.daily

data class DailyData(
    val DailyForecasts: List<DailyForecast>,
    val Headline: Headline
)