package com.impact.thebestweather.mappers

import com.impact.thebestweather.models.LocationSharedData
import com.impact.thebestweather.models.location.LocationItem
import com.impact.thebestweather.models.location.LocationItemData

class LocationItemMapper {
    fun toLocationItemData(locationItem: LocationItem) : LocationItemData {
        return LocationItemData(
            locationItem.AdministrativeArea,
            locationItem.Country,
            locationItem.DataSets,
            locationItem.EnglishName,
            locationItem.GeoPosition,
            locationItem.IsAlias,
            locationItem.Key,
            locationItem.LocalizedName,
            locationItem.PrimaryPostalCode,
            locationItem.Rank,
            locationItem.Region,
            locationItem.SupplementalAdminAreas,
            locationItem.TimeZone,
            locationItem.Type,
            locationItem.Version
        )
    }
}