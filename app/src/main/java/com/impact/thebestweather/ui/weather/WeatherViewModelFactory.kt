package com.impact.thebestweather.ui.weather

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.impact.thebestweather.usecases.GetWeatherUseCase

class WeatherViewModelFactory(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherViewModel(
            getWeatherUseCase,
            application = Application()
        ) as T
    }
}