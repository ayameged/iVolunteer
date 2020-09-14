package com.ivolunteer.ivolunteer.ui.needHelpUserFragments.needHelpUserNewHelpEvent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ivolunteer.ivolunteer.R
import com.ivolunteer.ivolunteer.resources.NetworkManager
import com.ivolunteer.ivolunteer.resources.StorageManager
import com.ivolunteer.ivolunteer.resources.StorageTypes
import com.ivolunteer.ivolunteer.types.Auth
import com.ivolunteer.ivolunteer.types.City
import com.ivolunteer.ivolunteer.types.Type
import com.ivolunteer.ivolunteer.util.Helper
import org.json.JSONObject

class NewHelpEventFragment : Fragment() {

  private lateinit var slideshowViewModel: NewHelpViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    slideshowViewModel =
      ViewModelProviders.of(this).get(NewHelpViewModel::class.java)
    val root = inflater.inflate(R.layout.fragment_new_help_event, container, false)

    var volunteers_types: ArrayList<String> = ArrayList()
    var volunteers_cities: ArrayList<String> = ArrayList()
    var volunteersTypesSpinner = root.findViewById<Spinner>(R.id.events_spinner)
    var volunteersCitiesSpinner = root.findViewById<Spinner>(R.id.city_spinner_new_event)

    NetworkManager.instance.get<List<Type>>("volunteertypes") { response, statusCode, error ->
      if (statusCode == 200) {
        if (response != null) {
          for(item in response){
            volunteers_types.add(item.type)
            }
          StorageManager.instance.set(StorageTypes.TYPES_LIST.toString(), response)
        }
        volunteersTypesSpinner?.post {
          volunteersTypesSpinner.adapter = activity?.applicationContext?.let { ArrayAdapter(it, R.layout.support_simple_spinner_dropdown_item, volunteers_types) }
        }
      }
    }

    NetworkManager.instance.get<List<City>>("NeedHelpCities") { response, statusCode, error ->
      if (statusCode == 200) {
        if (response != null) {
          for(item in response){
            volunteers_cities.add(item.city)
          }
          //new
          StorageManager.instance.set(StorageTypes.CITIES_LIST.toString(), response)
        }
        volunteersCitiesSpinner?.post {
          volunteersCitiesSpinner.adapter = activity?.applicationContext?.let { ArrayAdapter(it, R.layout.support_simple_spinner_dropdown_item, volunteers_cities) }
        }
      }
    }
    return root
  }


  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val updateButtonNeedHelpUser = view.findViewById<Button>(R.id.create_event_btn)

    updateButtonNeedHelpUser.setOnClickListener {


      val morningCheckBox = view.findViewById<CheckBox>(R.id.morning_check_box)
      val noonCheckBox = view.findViewById<CheckBox>(R.id.noon_check_box)
      val eveningCheckBox = view.findViewById<CheckBox>(R.id.evening_check_box)
      val volunteersCitiesSpinner = view.findViewById<Spinner>(R.id.city_spinner_new_event)
      val volunteersTypesSpinner = view.findViewById<Spinner>(R.id.events_spinner)
      val cityId = Helper.getCityId(volunteersCitiesSpinner.selectedItem.toString())
      val typeId = Helper.getTypeId(volunteersTypesSpinner.selectedItem.toString())


      val days= arrayOf<CheckBox>(view.findViewById<CheckBox>(R.id.sunday_check_box), view.findViewById<CheckBox>(R.id.monday_check_box),
        view.findViewById<CheckBox>(R.id.tuesday_check_box), view.findViewById<CheckBox>(R.id.wednesday_check_box),
        view.findViewById<CheckBox>(R.id.thursday_check_box), view.findViewById<CheckBox>(R.id.friday_check_box),
        view.findViewById<CheckBox>(R.id.saturday_check_box))
      val size = 8;
      var internalData: ArrayList<Int> = ArrayList()
      var i=1;
      for (checkBox in days){
        if (checkBox.isChecked){
          internalData.add(i)
        }
        i++;
      }

      val scheduleJson = JSONObject()
      scheduleJson.put("isMorning", morningCheckBox.isChecked)
      scheduleJson.put("isNoon", noonCheckBox.isChecked)
      scheduleJson.put("isEvening", eveningCheckBox.isChecked)

      scheduleJson.put("internalData", internalData.joinToString ( separator =";" ))

      val json = JSONObject()
      json.put("volunteerscheduler", scheduleJson)

      json.put("needhelpUserId",StorageManager.instance.get<String>(StorageTypes.USER_ID.toString()))

      json.put("volunteercityid", cityId)
      json.put("volunteertypeid", typeId)

      NetworkManager.instance.post<Auth>("volunteers", json) { response, statusCode, error ->
        if (statusCode != 200) {
          Log.i("LOG - error", error.toString())

        } else {
          Log.i("LOG - login", "SUCCESS")
        }
      }
    }
  }
}