package com.ivolunteer.ivolunteer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.ivolunteer.ivolunteer.resources.NetworkManager
import com.ivolunteer.ivolunteer.resources.StorageManager
import com.ivolunteer.ivolunteer.resources.StorageTypes
import com.ivolunteer.ivolunteer.types.City
import com.ivolunteer.ivolunteer.types.needhelpuser.NeedHelpUser
import com.ivolunteer.ivolunteer.types.volunteeruser.VolunteerUser
import com.ivolunteer.ivolunteer.util.Helper
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
        val genderSpinner = view.findViewById<Spinner>(R.id.gender_spinner)
        val firstName = view.findViewById<EditText>(R.id.first_name_text)
        val lastName = view.findViewById<EditText>(R.id.last_name_text)
        val phone = view.findViewById<EditText>(R.id.phone_text)
        val age = view.findViewById<EditText>(R.id.age_text)
        var cityId = 1

        update_btn.setOnClickListener{

            val json_create_user = JSONObject()
            val json_update_user = JSONObject()
            cityId = Helper.getCityId(citySpinner.selectedItem.toString())
            json_create_user.put("id", StorageManager.instance.get<String>(StorageTypes.USER_ID.toString()))
            json_update_user.put("id", StorageManager.instance.get<String>(StorageTypes.USER_ID.toString()))
            json_update_user.put("ApplicationUser", createApplicationUserJson(firstName, lastName, phone, age, genderSpinner))
            var isVolunteer = StorageManager.instance.get<Boolean>(StorageTypes.IS_VOLUNTEER.toString())
            if (isVolunteer != null && isVolunteer){
                json_create_user.put("volunteerusercityid", cityId)
                val json_rate = JSONObject()
                json_rate.put("AvgRate", 0)
                json_rate.put("numberofraters", 0)
                json_create_user.put("rate", json_rate)
                json_update_user.put("volunteerusercityid", cityId)
                createVolunteerUser(json_create_user, json_update_user)

            }
            else{
                json_create_user.put("needhelpcityid", cityId)
                json_update_user.put("needhelpcityid", cityId)
                createNeedHelpUser(json_create_user, json_update_user)
            }
        }
    }

//    private fun getCityId(cityName: String): Int{
//        var cityId = 1
//        var cities: List<City>? =
//            StorageManager.instance.get<List<City>>(StorageTypes.CITIES_LIST.toString())
//        if (cities != null) {
//            for (city: City in cities.iterator()) {
//                if (cityName == city.city) {
//                    cityId = city.needHelpCityId!!
//                }
//            }
//        }
//        return cityId
//    }

    private fun createVolunteerUser(json_create_user: JSONObject, json_update_user: JSONObject){
        NetworkManager.instance.post<VolunteerUser>("VolunteerUsers", json_create_user){ response, statusCode, error ->
            if (statusCode != 201) {
                Log.i("LOG - error in creating user ", error.toString())
            }
            else{
                StorageManager.instance.set(StorageTypes.RATE_ID.toString(), response!!.rate.rateId)
                json_update_user.put("rateId", StorageManager.instance.get<Int>(StorageTypes.RATE_ID.toString()))
                NetworkManager.instance.put<Int>("VolunteerUsers/"+StorageManager.instance.get<String>(StorageTypes.USER_ID.toString()), json_update_user){ response, statusCode, error ->
                    if (statusCode != 204){
                        Log.i("LOG - error in update user", error.toString())
//                        TODO: Add text about failure
                    }else{
                        Log.i("LOG - volunteer user created ", "")
                        val activityIntentVolunteer = Intent(this.context, VolunteerActivity::class.java)
                        startActivity(activityIntentVolunteer)
                    }
                }
            }
        }
    }

    private fun createApplicationUserJson(firstName: EditText, lastName: EditText, phone: EditText, age: EditText, genderSpinner: Spinner): JSONObject{
        val json_application_user = JSONObject()
        json_application_user.put("firstname", firstName.text)
        json_application_user.put("lastName", lastName.text)
        json_application_user.put("phoneNumber", phone.text)
        json_application_user.put("age", age.text)
        json_application_user.put("Gender", genderSpinner.selectedItem.toString())
        return json_application_user
    }

    private fun createNeedHelpUser(json_create_user: JSONObject, json_update_user: JSONObject){
        NetworkManager.instance.post<NeedHelpUser>("NeedHelpUsers", json_create_user){ response, statusCode, error ->
            if (statusCode != 201) {
                Log.i("LOG - error in creating user ", error.toString())
            }
            else{
                NetworkManager.instance.put<Int>("NeedHelpUsers/"+StorageManager.instance.get<String>(StorageTypes.USER_ID.toString()), json_update_user){ response, statusCode, error ->
                    if (statusCode != 204){
                        Log.i("LOG - error in update user", error.toString())
                    }else{
                        Log.i("LOG - need help user created ", "")
                        val activityIntentNeedHelpUser = Intent(this.context, NeedHelpActivity::class.java)
                        startActivity(activityIntentNeedHelpUser)
                    }
                }

            }
        }
    }
}