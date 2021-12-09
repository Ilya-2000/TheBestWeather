package com.impact.thebestweather.weather

import com.impact.thebestweather.models.Result
import com.impact.thebestweather.models.weather.Weather
import com.impact.thebestweather.models.weather.WeatherRequestData
import com.impact.thebestweather.models.weather.current.CurrentWeather
import com.impact.thebestweather.models.weather.daily.DailyData
import com.impact.thebestweather.models.weather.hourly.HourlyData
import io.reactivex.Single

interface WeatherRemoteSource {
    fun getWeather(weatherRequestData: WeatherRequestData): Single<Weather>


}