package com.impact.thebestweather.data.repository

import android.util.Log
import com.impact.thebestweather.data.WeatherSource
import com.impact.thebestweather.models.Resource
import com.impact.thebestweather.models.weather.Weather
import com.impact.thebestweather.models.weather.WeatherRequest
import com.impact.thebestweather.models.weather.current.CurrentWeather
import com.impact.thebestweather.models.weather.daily.DailyData
import com.impact.thebestweather.models.weather.hourly.HourlyData
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function3

class WeatherRepository() {
    companion object {
        private val TAG = "WeatherRepository"
        private val weatherSource = WeatherSource()
        /*lateinit var dailyData: DailyData
        lateinit var hourlyData: HourlyData
        lateinit var currentWeather: CurrentWeather*/

        /*private fun getDailyWeather(compositeDisposable: CompositeDisposable,
                                    weatherRequest: WeatherRequest): DailyData {
            weatherSource.getDailyWeather(compositeDisposable, weatherRequest)
            Log.d(TAG,"getWeather")

            return weatherSource.dailyWeatherLiveData.value!!
        }

        private fun getHourlyWeather(compositeDisposable: CompositeDisposable,
                                     weatherRequest: WeatherRequest): HourlyData {
            weatherSource.getHourlyWeather(compositeDisposable, weatherRequest)
            return weatherSource.hourlyWeatherLiveData.value!!
        }

        private fun getCurrentWeather(compositeDisposable: CompositeDisposable,
                                      weatherRequest: WeatherRequest): CurrentWeather {
            weatherSource.getCurrentWeather(compositeDisposable, weatherRequest)
            return weatherSource.currentWeatherLiveData.value!!
        }*/


    }
}