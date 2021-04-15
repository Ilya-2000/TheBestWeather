package com.impact.thebestweather.data.repository

import android.util.Log
import com.impact.thebestweather.data.WeatherSource
import com.impact.thebestweather.models.weather.WeatherRequest
import com.impact.thebestweather.models.weather.current.CurrentWeather
import com.impact.thebestweather.models.weather.daily.DailyData
import com.impact.thebestweather.models.weather.hourly.HourlyData
import io.reactivex.disposables.CompositeDisposable

class WeatherRepository() {
    companion object {
        private val TAG = "WeatherRepository"
        private val weatherSource = WeatherSource()
        var dailyData: DailyData? = null
        var hourlyData: HourlyData? = null
        var currentWeather: CurrentWeather? = null

        private fun getDailyWeather(compositeDisposable: CompositeDisposable,
                                    weatherRequest: WeatherRequest): DailyData? {
            weatherSource.getDailyWeather(compositeDisposable, weatherRequest)
            Log.d(TAG,"getWeather")

            return weatherSource.dailyWeatherLiveData.value
        }

        private fun getHourlyWeather(compositeDisposable: CompositeDisposable,
                                     weatherRequest: WeatherRequest): HourlyData? {
            weatherSource.getHourlyWeather(compositeDisposable, weatherRequest)
            return weatherSource.hourlyWeatherLiveData.value
        }

        private fun getCurrentWeather(compositeDisposable: CompositeDisposable,
                                      weatherRequest: WeatherRequest): CurrentWeather? {
            weatherSource.getCurrentWeather(compositeDisposable, weatherRequest)
            return weatherSource.currentWeatherLiveData.value
        }

        fun getWeather(compositeDisposable: CompositeDisposable,
        weatherRequest: WeatherRequest) {
            hourlyData = getHourlyWeather(compositeDisposable, weatherRequest)
            dailyData = getDailyWeather(compositeDisposable, weatherRequest)
            val request = weatherRequest.copy(id = weatherRequest.id, apiKey = weatherRequest.apiKey, language = weatherRequest.language,
                    details = weatherRequest.details, metric = "")
            currentWeather = getCurrentWeather(compositeDisposable, request)
        }
    }
}