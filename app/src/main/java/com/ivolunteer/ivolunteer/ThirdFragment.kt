package com.ivolunteer.ivolunteer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.ivolunteer.ivolunteer.resources.NetworkManager
import com.ivolunteer.ivolunteer.resources.StorageManager
import com.ivolunteer.ivolunteer.resources.StorageTypes
import com.ivolunteer.ivolunteer.types.City
import com.ivolunteer.ivolunteer.types.needhelpuser.NeedHelpUser
import com.ivolunteer.ivolunteer.types.volunteeruser.VolunteerUser
import kotlinx.android.synthetic.main.fragment_third.*
import org.json.JSONObject


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ThirdFragment : Fragment() {

    val genderSelection = arrayOf("Male", "Female")
    var cities_names: ArrayList<String> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val t=inflater.inflate(R.layout.fragment_third, container, false)
        val genderSpinner = t.findViewById<Spinner>(R.id.gender_spinner)
        genderSpinner?.adapter = activity?.applicationContext?.let { ArrayAdapter(it, R.layout.support_simple_spinner_dropdown_item, genderSelection) }
        val citySpinner = t.findViewById<Spinner>(R.id.city_spinner)

//        Get all cities from API
//        Create cities names array for apinner
//        Store cities in storage
        NetworkManager.instance.get<List<City>>("NeedHelpCities") { response, statusCode, error ->
            if(response != null) {
                for (city in response) {
                    cities_names.add(city.city)
                }
                StorageManager.instance.set(StorageTypes.CITIES_LIST.toString(), response)

                citySpinner.post {
                    citySpinner?.adapter = activity?.applicationContext?.let { ArrayAdapter(it, R.layout.support_simple_spinner_dropdown_item, cities_names) }
                }
            }
        }


       return t
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val citySpinner = view.findViewById<Spinner>(R.id.city_spinner)
        var cityId = 1

        update_btn.setOnClickListener{

            val json_create_user = JSONObject()

//            var userName = StorageManager.instance.get<String>(StorageTypes.USER_NAME.toString())
//            NetworkManager.instance.get<String>("applicationUsers/ByUserName?username="+userName){response, statusCode, error ->
//                if(statusCode == 200){
//                    StorageManager.instance.set(StorageTypes.USER_ID.toString(), response!!)
//                }
//                else{
//                    print(error)
//                }
//            }
//            var cities :List<City>? = StorageManager.instance.get<List<City>>(StorageTypes.CITIES_LIST.toString())
//            if (cities != null) {
//                for(city:City in cities.iterator()){
//                    if (citySpinner.selectedItem.toString() == city.city){
//                        cityId = city.needHelpCityId!!
//                    }
//                }
//            }
            cityId = getCityId(citySpinner.selectedItem.toString())
            json_create_user.put("id", StorageManager.instance.get<String>(StorageTypes.USER_ID.toString()))
            var isVolunteer = StorageManager.instance.get<Boolean>(StorageTypes.IS_VOLUNTEER.toString())
//            val json_city = JSONObject()
//            json_city.put("city",citySpinner.selectedItem.toString())
            if (isVolunteer != null && isVolunteer){
//                json_create_user.put("volunteerusercity", json_city)
                json_create_user.put("volunteerusercityid", cityId)
                val json_rate = JSONObject()
                json_rate.put("AvgRate", 0)
                json_rate.put("numberofraters", 0)
                json_create_user.put("rate", json_rate)
                NetworkManager.instance.post<VolunteerUser>("VolunteerUsers", json_create_user){ response, statusCode, error ->
                    print(response)
                    if (statusCode != 200) {
                        print(error)
                    }
                }
            }
            else{
                json_create_user.put("needhelpcityid", cityId)
                NetworkManager.instance.post<NeedHelpUser>("NeedHelpUsers", json_create_user){ response, statusCode, error ->
                    print(response)
                    if (statusCode != 200) {
                        print(error)
                    }
                }
            }
//            TODO: create need help / volunteer user according storage
//            TODO: Add post request according to user type

        }

    }

    private fun getCityId(cityName: String): Int{
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