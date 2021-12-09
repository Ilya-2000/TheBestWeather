package com.impact.thebestweather.models.weather

import com.impact.thebestweather.models.weather.current.CurrentWeather
import com.impact.thebestweather.models.weather.daily.DailyData
import com.impact.thebestweather.models.weather.hourly.HourlyData

data class Weather(
        val hourlyData: HourlyData,
        val dailyData: DailyData,
        val currentWeather: CurrentWeather
)
