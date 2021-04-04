package com.impact.thebestweather.data

import com.impact.thebestweather.models.DailyWeather
import com.impact.thebestweather.models.WeatherRequest
import com.impact.thebestweather.network.WeatherApiService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WeatherSource(compositeDisposable: CompositeDisposable) {
    private val TAG = "WeatherSource"
    private val weatherApiService by lazy {
        WeatherApiService.create()
    }

    /*fun getWeather(weatherRequest: WeatherRequest) {
        weatherApiService.getOneCallWeather(weatherRequest.lat, weatherRequest.lon,
            weatherRequest.exclude, weatherRequest.appid)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observable<DailyWeather>())
    }*/
}