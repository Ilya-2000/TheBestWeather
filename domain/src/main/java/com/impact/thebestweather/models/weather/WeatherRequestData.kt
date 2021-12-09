package com.impact.thebestweather.models.weather

data class WeatherRequestData(
        val id: String,
        val apiKey: String,
        val language: String,
        val details: String,
        val metric: String
)
