package com.impact.thebestweather.di

import android.content.Context
import com.impact.thebestweather.city.CityRemoteSource
import com.impact.thebestweather.city.CityRepositoryImpl
import com.impact.thebestweather.city.LocationLocalStorageImpl
import com.impact.thebestweather.weather.WeatherRemoteSource
import com.impact.thebestweather.weather.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(weatherRemoteSource: WeatherRemoteSource)
    = WeatherRepositoryImpl(weatherRemoteSource = weatherRemoteSource)

    @Provides
    @Singleton
    fun provideLocationLocalStorage(@ApplicationContext context: Context)
    = LocationLocalStorageImpl(context = context)

    @Provides
    @Singleton
    fun provideCityRepository(
        cityRemoteSource: CityRemoteSource,
        locationLocalStorageImpl: LocationLocalStorageImpl) = CityRepositoryImpl(
        cityRemoteSource = cityRemoteSource,
        locationLocalStorage = locationLocalStorageImpl)


}