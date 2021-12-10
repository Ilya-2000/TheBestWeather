package com.impact.thebestweather.di

import com.impact.thebestweather.CityRepository
import com.impact.thebestweather.WeatherRepository
import com.impact.thebestweather.usecases.GetCitiesUseCase
import com.impact.thebestweather.usecases.GetSelectedCityUseCase
import com.impact.thebestweather.usecases.GetWeatherUseCase
import com.impact.thebestweather.usecases.SetSelectCityUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {
    @Provides
    fun provideGetWeatherUseCase(weatherRepository: WeatherRepository)
    = GetWeatherUseCase(weatherRepository = weatherRepository)

    @Provides
    fun provideGetCitiesUseCase(cityRepository: CityRepository)
    = GetCitiesUseCase(cityRepository = cityRepository)

    @Provides
    fun provideGetSelectedCityUseCase(cityRepository: CityRepository)
    = GetSelectedCityUseCase(cityRepository = cityRepository)

    @Provides
    fun provideSetSelectedCityUseCase(cityRepository: CityRepository)
    = SetSelectCityUseCase(cityRepository = cityRepository)
}