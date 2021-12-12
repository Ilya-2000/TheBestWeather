package com.impact.thebestweather.weather

import com.impact.thebestweather.WeatherRepository
import com.impact.thebestweather.models.weather.Weather
import com.impact.thebestweather.models.weather.WeatherRequestData
import io.reactivex.Single

class WeatherRepositoryImpl(private val weatherRemoteSource: WeatherRemoteSource) : WeatherRepository {
    override fun getWeather(data: WeatherRequestData): Single<Weather> {
        return weatherRemoteSource.getWeather(weatherRequestData = data)
    }
}