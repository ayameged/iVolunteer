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
import com.ivolunteer.ivolunteer.types.volunteercities.VolunteerCityItem
import com.ivolunteer.ivolunteer.types.volunteers.VolunteersTypeItem
import com.ivolunteer.ivolunteer.util.Helper
import kotlinx.android.synthetic.main.custom_list.*
import kotlinx.android.synthetic.main.fragment_new_help_event.*
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
//    val textView: TextView = root.findViewById(R.id.text_slideshow)
//    slideshowViewModel.text.observe(viewLifecycleOwner, Observer {
//      textView.text = it
//    })

    var volunteers_types: ArrayList<String> = ArrayList()
    var volunteers_cities: ArrayList<String> = ArrayList()
    var volunteersTypesSpinner = root.findViewById<Spinner>(R.id.events_spinner)
    var volunteersCitiesSpinner = root.findViewById<Spinner>(R.id.city_spinner_new_event)
    NetworkManager.instance.get<List<VolunteersTypeItem>>("VolunteerTypes") { response, statusCode, error ->
      if (statusCode == 200) {
        if (response != null) {
          for(item in response){
            volunteers_types.add(item.type)
            }
        }
        volunteersTypesSpinner?.post {
          volunteersTypesSpinner.adapter = activity?.applicationContext?.let { ArrayAdapter(it, R.layout.support_simple_spinner_dropdown_item, volunteers_types) }
         // volunteersTypesSpinner.setSelection(volunteers_types.indexOf(type.toString()))
        }

      }

    }

    NetworkManager.instance.get<List<VolunteerCityItem>>("VolunteerCities") { response, statusCode, error ->
      if (statusCode == 200) {
        if (response != null) {
          for(item in response){
            volunteers_cities.add(item.city)
          }
        }
        volunteersCitiesSpinner?.post {
          volunteersCitiesSpinner.adapter = activity?.applicationContext?.let { ArrayAdapter(it, R.layout.support_simple_spinner_dropdown_item, volunteers_cities) }
        // volunteersCitiesSpinner.setSelection(volunteers_cities.indexOf(city.toString()))
        }

      }

    }





    val updateButtonNeedHelpUser = root.findViewById<Button>(R.id.update_btn_need_help_user)


    updateButtonNeedHelpUser.setOnClickListener {


      val morningCheckBox = root.findViewById<CheckBox>(R.id.morning_check_box)
      val noonCheckBox = root.findViewById<CheckBox>(R.id.noon_checkbox)
      val eveningCheckBox = root.findViewById<CheckBox>(R.id.evening_check_box)
      var cityId = Helper.getCityId(volunteersCitiesSpinner.selectedItem.toString())
      var typeId = Helper.getTypeId(volunteersTypesSpinner.selectedItem.toString())


      //val sundayCheckBox = root.findViewById<CheckBox>(R.id.sunday_check_box)
      // val mondayCheckBox = root.findViewById<CheckBox>(R.id.monday_check_box)
      // val tuesdayCheckBox = root.findViewById<CheckBox>(R.id.tuesday_check_box)
      // val wednesdayCheckBox = root.findViewById<CheckBox>(R.id.wednesday_check_box)
      //val thursdayCheckBox = root.findViewById<CheckBox>(R.id.thursday_check_box)
      // val fridayCheckBox = root.findViewById<CheckBox>(R.id.friday_check_box)
      // val saturdayCheckBox = root.findViewById<CheckBox>(R.id.saturday_check_box)





      val days= arrayOf<CheckBox>(root.findViewById<CheckBox>(R.id.sunday_check_box), root.findViewById<CheckBox>(R.id.monday_check_box),
        root.findViewById<CheckBox>(R.id.tuesday_check_box), root.findViewById<CheckBox>(R.id.wednesday_check_box),
        root.findViewById<CheckBox>(R.id.thursday_check_box), root.findViewById<CheckBox>(R.id.friday_check_box),
        root.findViewById<CheckBox>(R.id.saturday_check_box))
      val size = 8;
      val internalData= arrayOfNulls<Int>(size)
      var i=1;
      var j=1;
      for (checkBox in days)
      {
        if (checkBox.isChecked==true)
        {
          internalData[j]=i;
          j++;
        }
        i++;
      }







      val scheduleJson = JSONObject()
      scheduleJson.put("isMorning", morningCheckBox.isChecked)
      scheduleJson.put("isNoon", noonCheckBox.isChecked)
      scheduleJson.put("isEvening", eveningCheckBox.isChecked)

     scheduleJson.put("internalData", internalData.joinToString ( separator =";" ))
      //scheduleJson.put("InternalData", "1;2;3")

      val json = JSONObject()
      json.put("volunteerscheduler", scheduleJson)

      //json.put("needhelpUserId", "abc35d18-7670-4b18-b8cb-25feb70dc746")
      json.put("needhelpUserId",StorageManager.instance.get<String>(StorageTypes.USER_ID.toString()))

      json.put("volunteercityid", cityId)
      //json.put("volunteerCityid", 1)
      //json.put("volunteerTypeid", 2)
      json.put("volunteertypeid", typeId)

      NetworkManager.instance.post<Auth>(path = "volunteers", body = json) { response, statusCode, error ->
        if (statusCode != 200) {
          Log.i("LOG - error", error.toString())

          //loginError.post {
          //  loginError.visibility = View.VISIBLE
          //   }
        } else {
          Log.i("LOG - login", "SUCCESS")
        }

      }
    }
    return root
  }
}