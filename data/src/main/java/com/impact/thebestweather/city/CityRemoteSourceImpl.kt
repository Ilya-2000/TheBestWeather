package com.impact.thebestweather.city

import com.impact.thebestweather.models.location.CityRequest
import com.impact.thebestweather.models.location.Location
import com.impact.thebestweather.network.CityApiService
import com.impact.thebestweather.network.WeatherApiService
import io.reactivex.Single

class CityRemoteSourceImpl() : CityRemoteSource {
    private val cityApiService by lazy {
        CityApiService.create()
    }

    override fun getCitiesFromNetwork(cityRequest: CityRequest): Single<Location> {
        return cityApiService.searchCity(
            cityRequest.api,
            cityRequest.query,
            cityRequest.language,
            cityRequest.details)
    }
}