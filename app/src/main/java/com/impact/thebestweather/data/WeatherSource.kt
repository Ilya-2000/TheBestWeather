package com.impact.thebestweather.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.impact.thebestweather.models.DailyWeather
import com.impact.thebestweather.models.WeatherRequest
import com.impact.thebestweather.network.WeatherApiService
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class WeatherSource() {
    private val TAG = "WeatherSource"
    private var dailyWeatherData: DailyWeather? = null
    private val _weatherLiveData = MutableLiveData<DailyWeather>()
    val weatherLiveData: LiveData<DailyWeather>
        get() = _weatherLiveData
    private val weatherApiService by lazy {
        WeatherApiService.create()
    }


    fun getWeather(compositeDisposable: CompositeDisposable, weatherRequest: WeatherRequest) {
        compositeDisposable.add(weatherApiService.getOneCallWeather(weatherRequest.lat, weatherRequest.lon,
                weatherRequest.exclude, weatherRequest.appid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<DailyWeather>(){
                    override fun onNext(t: DailyWeather) {
                        Log.d(TAG, "onNext: $t")
                        setDailyWeatherData(t)
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "onError: $e")
                    }

                    override fun onComplete() {
                        Log.d(TAG, "onComplete")
                    }

                    /*override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "onSubscribe: $d")
                    }*/

                })
        )
    }

    private fun setDailyWeatherData(dailyWeather: DailyWeather) {
        _weatherLiveData.value = dailyWeather
    }

    fun getDailyWeatherData(): DailyWeather? {
        return dailyWeatherData
    }

}