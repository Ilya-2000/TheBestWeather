package com.impact.thebestweather.usecases

import com.impact.thebestweather.WeatherRepository
import com.impact.thebestweather.models.weather.Weather
import com.impact.thebestweather.models.weather.WeatherRequestData
import io.reactivex.Single

class GetWeatherUseCase(private val weatherRepository: WeatherRepository) {
    fun execute(data: WeatherRequestData) = weatherRepository.getWeather(weatherRequestData = data)
}