package com.impact.thebestweather

import com.impact.thebestweather.models.location.CityRequest
import com.impact.thebestweather.models.location.Location

interface CityRepository {
    fun getCities(cityRequest: CityRequest): Location
}