package com.impact.thebestweather.data.repository

import com.impact.thebestweather.data.WeatherSource
import com.impact.thebestweather.models.DailyWeather
import com.impact.thebestweather.models.WeatherRequest
import io.reactivex.disposables.CompositeDisposable

class WeatherRepository() {
    companion object {
        private val TAG = "WeatherRepository"
        private val weatherSource = WeatherSource()
        fun getWeather( compositeDisposable: CompositeDisposable,
                        weatherRequest: WeatherRequest): DailyWeather {
            weatherSource.getWeather(compositeDisposable, weatherRequest)
            return weatherSource.getDailyWeatherData()
        }
    }
}