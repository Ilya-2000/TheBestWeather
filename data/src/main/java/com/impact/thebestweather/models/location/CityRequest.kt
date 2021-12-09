package com.impact.thebestweather.models.location

data class CityRequest(
    val api: String,
    val query: String,
    val language: String,
    val details: String
)
