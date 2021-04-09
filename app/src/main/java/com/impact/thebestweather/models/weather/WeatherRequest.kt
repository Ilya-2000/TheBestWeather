package com.impact.thebestweather.models.weather

data class WeatherRequest(
        val apiKey: String,
        val language: String,
        val details: String,
        val metric: String
)
