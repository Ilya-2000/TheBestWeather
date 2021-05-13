package com.impact.thebestweather.data

import com.impact.thebestweather.network.CityApiService
import com.impact.thebestweather.network.WeatherApiService
import io.reactivex.disposables.CompositeDisposable

class CitySource {
    private val TAG = "CitySource"
    private val cityApiService by lazy {
        CityApiService.create()
    }

    fun searchCity(compositeDisposable: CompositeDisposable, ) {
        //cityApiService.searchCity()
    }
}