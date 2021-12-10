package com.impact.thebestweather.city

import com.impact.thebestweather.models.location.CityRequest
import com.impact.thebestweather.models.location.Location
import io.reactivex.Single

interface CityRemoteSource {
    fun getCitiesFromNetwork(cityRequestData: CityRequest) : Single<Location>
}