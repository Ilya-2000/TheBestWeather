package com.impact.thebestweather.city

import com.impact.thebestweather.models.location.CityRequest
import com.impact.thebestweather.models.location.Location
import com.impact.thebestweather.network.CityApiService
import io.reactivex.Single

class CityRemoteSourceImpl() : CityRemoteSource {
    private val cityApiService by lazy {
        CityApiService.create()
    }

    override fun getCitiesFromNetwork(cityRequestData: CityRequest): Single<Location> {
        return cityApiService.searchCity(
            cityRequestData.api,
            cityRequestData.query,
            cityRequestData.language,
            cityRequestData.details)
    }
}