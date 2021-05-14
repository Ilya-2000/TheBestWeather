package com.impact.thebestweather.data

import android.util.Log
import com.impact.thebestweather.models.location.LocationRequest
import com.impact.thebestweather.network.CityApiService
import com.impact.thebestweather.network.WeatherApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CitySource {
    private val TAG = "CitySource"
    private val cityApiService by lazy {
        CityApiService.create()
    }

    fun searchCity(compositeDisposable: CompositeDisposable, locationRequest: LocationRequest) {
        compositeDisposable.add(cityApiService.searchCity(locationRequest.api,
            locationRequest.queryText,
            locationRequest.language,
            locationRequest.details)
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    Log.d(TAG, it[0].toString())
                },
                {
                    Log.d(TAG, it.message.toString())
                }
            ))

    }
}