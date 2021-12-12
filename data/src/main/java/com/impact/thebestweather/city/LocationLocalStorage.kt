package com.impact.thebestweather.city

import com.impact.thebestweather.models.LocationSharedData
import com.impact.thebestweather.models.location.LocationItem

interface LocationLocalStorage {
    fun saveSelectCity(locationItem: LocationItem)

    fun getSelectedCity(): LocationSharedData?
}