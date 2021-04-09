package com.impact.thebestweather.data.repository

import android.util.Log
import com.impact.thebestweather.data.WeatherSource
import com.impact.thebestweather.models.weather.WeatherRequest
import com.impact.thebestweather.models.weather.daily.DailyData
import io.reactivex.disposables.CompositeDisposable

class WeatherRepository() {
    companion object {
        private val TAG = "WeatherRepository"
        private val weatherSource = WeatherSource()

        fun getDailyWeather( compositeDisposable: CompositeDisposable,
                        weatherRequest: WeatherRequest): DailyData? {
            weatherSource.getDailyWeather(compositeDisposable, weatherRequest)
            Log.d(TAG,"getWeather")

            return weatherSource.weatherLiveData.value
        }
    }
}