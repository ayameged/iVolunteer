package com.ivolunteer.ivolunteer.util

import com.ivolunteer.ivolunteer.resources.NetworkManager
import com.ivolunteer.ivolunteer.resources.StorageManager
import com.ivolunteer.ivolunteer.resources.StorageTypes
import com.ivolunteer.ivolunteer.types.City
import com.ivolunteer.ivolunteer.types.Type

class Helper {

    companion object {
        fun getCityId(cityName: String): Int{
            //TODO: Duplicate - move to generic (User?)
            if (StorageManager.instance.get<List<City>>(StorageTypes.CITIES_LIST.toString())==null)
            {
                insertCitiesToStorage()
            }
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

        fun getTypeId(typeName: String): Int{
            //TODO: Duplicate - move to generic (User?)
            if (StorageManager.instance.get<List<Type>>(StorageTypes.TYPES_LIST.toString())==null)
            {
                insertTypesToStorage()
            }
            var typeId = 1
            var voltypes: List<Type>? =
                StorageManager.instance.get<List<Type>>(StorageTypes.TYPES_LIST.toString())
            if (voltypes != null) {
                for (type: Type in voltypes.iterator()) {
                    if (typeName == type.type) {
                        typeId = type.volunteerTypeId!!
                    }
                }
            }
            return typeId
        }

        private fun insertCitiesToStorage()
        {
            NetworkManager.instance.get<List<City>>("NeedHelpCities") { response, statusCode, error ->
                if (response != null)
                {
                    StorageManager.instance.set(StorageTypes.CITIES_LIST.toString(), response)
                }
            }
        }


        private fun insertTypesToStorage()
        {
            NetworkManager.instance.get<List<City>>("NeedHelpTypes") { response, statusCode, error ->
                if (response != null)
                {
                    StorageManager.instance.set(StorageTypes.TYPES_LIST.toString(), response)
                }
            }
        }
    }
}