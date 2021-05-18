package com.impact.thebestweather.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.impact.thebestweather.models.location.Location
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

    private val _cityListLiveData = MutableLiveData<Location>()
    val cityListLiveData: LiveData<Location>
        get() = _cityListLiveData

    fun searchCity(compositeDisposable: CompositeDisposable, locationRequest: LocationRequest): LiveData<Location> {
        compositeDisposable.add(cityApiService.searchCity(locationRequest.api,
            locationRequest.queryText,
            locationRequest.language,
            locationRequest.details)
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    Log.d(TAG, it[0].toString())
                    _cityListLiveData.postValue(it)
                },
                {
                    Log.d(TAG, it.message.toString())
                }
            ))
        return cityListLiveData

    }
}