package com.impact.thebestweather.city

import com.impact.thebestweather.models.location.LocationItemData
import com.impact.thebestweather.models.LocationSharedData

interface LocationLocalStorage {
    fun saveSelectCity(locationItemData: LocationItemData)

    fun getSelectedCity(): LocationSharedData?
}