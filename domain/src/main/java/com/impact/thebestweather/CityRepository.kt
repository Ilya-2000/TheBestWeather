package com.impact.thebestweather

import com.impact.thebestweather.models.location.CityRequest
import com.impact.thebestweather.models.location.Location
import com.impact.thebestweather.models.location.LocationItem

interface CityRepository {
    fun getCities(cityRequest: CityRequest): Location

    fun selectCity(locationItem: LocationItem)
}