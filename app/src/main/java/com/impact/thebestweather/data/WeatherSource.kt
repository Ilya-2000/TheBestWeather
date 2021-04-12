package com.impact.thebestweather.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.impact.thebestweather.models.weather.WeatherRequest
import com.impact.thebestweather.models.weather.daily.DailyData
import com.impact.thebestweather.models.weather.hourly.HourlyData
import com.impact.thebestweather.network.WeatherApiService
import com.impact.thebestweather.utils.Constant
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class WeatherSource() {
    private val TAG = "WeatherSource"
    private var dailyWeatherData: DailyData? = null
    private val _dailyWeatherLiveData = MutableLiveData<DailyData>()
    val dailyWeatherLiveData: LiveData<DailyData>
        get() = _dailyWeatherLiveData

    private val _hourlyWeatherLiveData = MutableLiveData<HourlyData>()
    val hourlyWeatherLiveData: LiveData<HourlyData>
        get() = _hourlyWeatherLiveData
    private val weatherApiService by lazy {
        WeatherApiService.create()
    }


    fun getDailyWeather(compositeDisposable: CompositeDisposable, weatherRequest: WeatherRequest) {
        compositeDisposable.add(weatherApiService.getDailyWeatherFromNetwork(weatherRequest.id, weatherRequest.apiKey,
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

    fun getHourlyWeather(compositeDisposable: CompositeDisposable, weatherRequest: WeatherRequest) {
        compositeDisposable.add(weatherApiService.getHourlyWeatherFromNetwork(weatherRequest.id,
        weatherRequest.apiKey, weatherRequest.language, weatherRequest.details, weatherRequest.metric)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<HourlyData>(){
                    override fun onNext(t: HourlyData) {
                        Log.d(TAG, "getHourlyWeather: onNext/ $t")
                        setHourlyWeatherData(t)
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "getHourlyWeather: onError/ $e")
                    }

                    override fun onComplete() {
                        Log.d(TAG, "getHourlyWeather: onComplete")
                    }

                }))
    }

    private fun setDailyWeatherData(dailyWeather: DailyData) {
        _dailyWeatherLiveData.value = dailyWeather
    }

    private fun setHourlyWeatherData(hourlyWeather: HourlyData) {
        _hourlyWeatherLiveData.value = hourlyWeather
    }


}