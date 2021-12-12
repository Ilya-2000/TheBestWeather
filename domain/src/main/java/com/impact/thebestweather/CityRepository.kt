package com.impact.thebestweather

import com.impact.thebestweather.models.LocationShared
import com.impact.thebestweather.models.location.CityRequest
import com.impact.thebestweather.models.location.Location
import com.impact.thebestweather.models.location.LocationItem
import io.reactivex.Single

interface CityRepository {
    fun getCities(cityRequest: CityRequest): Single<Location>

    fun setSelectCity(locationItem: LocationItem)

    fun getSelectedCity(): LocationShared?
}