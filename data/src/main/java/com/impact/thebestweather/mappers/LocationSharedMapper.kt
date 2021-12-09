package com.impact.thebestweather.mappers

import com.impact.thebestweather.models.LocationShared
import com.impact.thebestweather.models.LocationSharedData

class LocationSharedMapper {
    fun toLocationShared(locationSharedData: LocationSharedData?): LocationShared? {
        return locationSharedData?.cityKey?.let { LocationShared(it, locationSharedData.cityName) }
    }
}