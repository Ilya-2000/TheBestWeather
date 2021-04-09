package com.impact.thebestweather.models.location

data class TimeZone(
    val Code: String,
    val GmtOffset: Int,
    val IsDaylightSaving: Boolean,
    val Name: String,
    val NextOffsetChange: Any
)