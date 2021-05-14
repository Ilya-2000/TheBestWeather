package com.impact.thebestweather.models.location

data class LocationRequest(
    val api: String,
    val queryText: String,
    val language: String,
    val details: String

)
