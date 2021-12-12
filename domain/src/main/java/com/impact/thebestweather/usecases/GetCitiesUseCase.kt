package com.impact.thebestweather.usecases

import com.impact.thebestweather.CityRepository
import com.impact.thebestweather.models.location.CityRequest
import com.impact.thebestweather.models.location.Location

class GetCitiesUseCase(private val cityRepository: CityRepository) {
    fun execute(data: CityRequest) = cityRepository.getCities(cityRequest = data)
}