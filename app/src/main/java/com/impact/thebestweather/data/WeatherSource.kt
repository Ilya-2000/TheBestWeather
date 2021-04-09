package com.impact.thebestweather.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.impact.thebestweather.models.weather.WeatherRequest
import com.impact.thebestweather.models.weather.daily.DailyData
import com.impact.thebestweather.network.WeatherApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class WeatherSource() {
    private val TAG = "WeatherSource"
    private var dailyWeatherData: DailyData? = null
    private val _weatherLiveData = MutableLiveData<DailyData>()
    val weatherLiveData: LiveData<DailyData>
        get() = _weatherLiveData
    private val weatherApiService by lazy {
        WeatherApiService.create()
    }


    fun getWeather(compositeDisposable: CompositeDisposable, weatherRequest: WeatherRequest) {
        compositeDisposable.add(weatherApiService.getOneCallWeather(weatherRequest.id, weatherRequest.apiKey,
        weatherRequest.language, weatherRequest.details, weatherRequest.metric)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<DailyData>(){
                    override fun onNext(t: DailyData) {
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

    private fun setDailyWeatherData(dailyWeather: DailyData) {
        _weatherLiveData.value = dailyWeather
    }

    fun getDailyWeatherData(): DailyData? {
        return dailyWeatherData
    }

}