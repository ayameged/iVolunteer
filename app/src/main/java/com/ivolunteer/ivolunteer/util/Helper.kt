package com.ivolunteer.ivolunteer.util

import com.ivolunteer.ivolunteer.resources.NetworkManager
import com.ivolunteer.ivolunteer.resources.StorageManager
import com.ivolunteer.ivolunteer.resources.StorageTypes
import com.ivolunteer.ivolunteer.types.City

class Helper {

    companion object {
        fun getCityId(cityName: String): Int{
            //TODO: Duplicate - move to generic (User?)
            var cityId = 1
            var cities: List<City>? =
                StorageManager.instance.get<List<City>>(StorageTypes.CITIES_LIST.toString())
            if (cities != null) {
                for (city: City in cities.iterator()) {
                    if (cityName == city.city) {
                        cityId = city.needHelpCityId!!
                    }
                }
            }
            return cityId
        }
    }
}