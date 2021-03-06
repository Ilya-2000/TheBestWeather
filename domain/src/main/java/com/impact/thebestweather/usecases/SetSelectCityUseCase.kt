package com.impact.thebestweather.usecases

import com.impact.thebestweather.CityRepository
import com.impact.thebestweather.models.location.LocationItem

class SetSelectCityUseCase(private val cityRepository: CityRepository) {
    fun execute(data: LocationItem) = cityRepository.setSelectCity(locationItem = data)

}