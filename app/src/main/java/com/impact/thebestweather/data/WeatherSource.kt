package com.impact.thebestweather.data

import android.util.Log
import com.impact.thebestweather.models.DailyWeather
import com.impact.thebestweather.models.WeatherRequest
import com.impact.thebestweather.network.WeatherApiService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class WeatherSource(private val compositeDisposable: CompositeDisposable) {
    private val TAG = "WeatherSource"
    private val weatherApiService by lazy {
        WeatherApiService.create()
    }

    fun getWeather(weatherRequest: WeatherRequest) {
        compositeDisposable.add(weatherApiService.getOneCallWeather(weatherRequest.lat, weatherRequest.lon,
                weatherRequest.exclude, weatherRequest.appid)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<DailyWeather>(){
                    override fun onNext(t: DailyWeather) {
                        Log.d(TAG, "onNext: $t")
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "onError: $e")
                    }

                    override fun onComplete() {
                        Log.d(TAG, "onComplete")
                    }

                })
        )
    }
}