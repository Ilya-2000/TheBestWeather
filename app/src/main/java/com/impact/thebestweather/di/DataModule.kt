package com.impact.thebestweather.di

import android.content.Context
import com.impact.thebestweather.city.CityRemoteSource
import com.impact.thebestweather.city.CityRepositoryImpl
import com.impact.thebestweather.city.LocationLocalStorageImpl
import com.impact.thebestweather.weather.WeatherRemoteSource
import com.impact.thebestweather.weather.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
class DataModule {
    @Provides
    fun provideWeatherRepository(weatherRemoteSource: WeatherRemoteSource)
    = WeatherRepositoryImpl(weatherRemoteSource = weatherRemoteSource)
    @Provides
    fun provideLocationLocalStorage(@ApplicationContext context: Context)
    = LocationLocalStorageImpl(context = context)
    @Provides
    fun provideCityRepository(
        cityRemoteSource: CityRemoteSource,
        locationLocalStorageImpl: LocationLocalStorageImpl) = CityRepositoryImpl(
        cityRemoteSource = cityRemoteSource,
        locationLocalStorage = locationLocalStorageImpl)


}