package com.impact.thebestweather.city

import com.impact.thebestweather.CityRepository
import com.impact.thebestweather.mappers.LocationItemMapper
import com.impact.thebestweather.mappers.LocationSharedMapper
import com.impact.thebestweather.models.LocationShared
import com.impact.thebestweather.models.LocationSharedData
import com.impact.thebestweather.models.location.CityRequest
import com.impact.thebestweather.models.location.Location
import com.impact.thebestweather.models.location.LocationItem
import io.reactivex.Single

class CityRepositoryImpl(private val cityRemoteSource: CityRemoteSource,
                         private val locationLocalStorage: LocationLocalStorage) : CityRepository {
    override fun getCities(cityRequest: CityRequest): Single<Location> {
        return cityRemoteSource.getCitiesFromNetwork(cityRequest)
    }

    override fun setSelectCity(locationItem: LocationItem) {
        locationLocalStorage.saveSelectCity(LocationItemMapper().toLocationItemData(locationItem))
    }

    override fun getSelectedCity(): LocationShared? {
        return LocationSharedMapper().toLocationShared(locationLocalStorage.getSelectedCity())
    }
}