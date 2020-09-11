package com.ivolunteer.ivolunteer.ui.volunteerUserFragments.volunteerUserSearchForVolunteers

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
import com.ivolunteer.ivolunteer.types.City
import com.ivolunteer.ivolunteer.types.Type
import com.ivolunteer.ivolunteer.types.VolunteerWithSched.searchedVolunteerItem
import com.ivolunteer.ivolunteer.ui.needHelpUserFragments.needHelpUserMyActivities.MyListAdapter
import com.ivolunteer.ivolunteer.ui.needHelpUserFragments.needHelpUserMyActivities.SearchForVolunteersViewModel
import com.ivolunteer.ivolunteer.util.Helper

class SearchForVolunteerFragment : Fragment() {

  private lateinit var galleryViewModel: SearchForVolunteersViewModel


  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    galleryViewModel =
      ViewModelProviders.of(this).get(SearchForVolunteersViewModel::class.java)
    val root = inflater.inflate(R.layout.fragment_my_volunteers, container, false)
    var listView = root.findViewById<ListView>(R.id.search_volunteers_list_view)



    var volunteers_types: ArrayList<String> = ArrayList()
    var volunteers_cities: ArrayList<String> = ArrayList()
    var volunteersTypesSpinner = root.findViewById<Spinner>(R.id.search_events_spinner)
    var volunteersCitiesSpinner = root.findViewById<Spinner>(R.id.search_city_spinner_new_event)

    NetworkManager.instance.get<List<Type>>("volunteertypes") { response, statusCode, error ->
      if (statusCode == 200) {
        if (response != null) {
          volunteers_types.add("")
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
          volunteers_cities.add("")
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

    val searchVolunteersButton = view.findViewById<Button>(R.id.search_volunteers_btn)
   var listView = view.findViewById<ListView>(R.id.search_volunteers_list_view)

    searchVolunteersButton.setOnClickListener {


      val morningCheckBox = view.findViewById<CheckBox>(R.id.search_morning_check_box)
      val noonCheckBox = view.findViewById<CheckBox>(R.id.search_noon_checkbox)
      val eveningCheckBox = view.findViewById<CheckBox>(R.id.search_evening_check_box)
      val volunteersCitiesSpinner = view.findViewById<Spinner>(R.id.search_city_spinner_new_event)
      val volunteersTypesSpinner = view.findViewById<Spinner>(R.id.search_events_spinner)
      val cityId = Helper.getCityId(volunteersCitiesSpinner.selectedItem.toString())
      val typeId = Helper.getTypeId(volunteersTypesSpinner.selectedItem.toString())


      val days = arrayOf<CheckBox>(
        view.findViewById<CheckBox>(R.id.search_sunday_check_box),
        view.findViewById<CheckBox>(R.id.search_monday_check_box),
        view.findViewById<CheckBox>(R.id.search_tuesday_check_box),
        view.findViewById<CheckBox>(R.id.search_wednesday_check_box),
        view.findViewById<CheckBox>(R.id.search_thursday_check_box),
        view.findViewById<CheckBox>(R.id.search_friday_check_box),
        view.findViewById<CheckBox>(R.id.search_saturday_check_box)
      )
      val size = 8;
      var internalData: ArrayList<Int> = ArrayList()
      var i = 1;
      for (checkBox in days) {
        if (checkBox.isChecked) {
          internalData.add(i)
        }
        i++;
      }


      // StorageManager.instance.get<String>(StorageTypes.USER_ID.toString()))
      NetworkManager.instance.get<List<searchedVolunteerItem>>("volunteers/byAll?schedTime=1;0;0&schedDays=1;3;5&cityId=2&typeId=1") { response, statusCode, error ->
        val cities= arrayOfNulls<String>(response!!.size)
        val type= arrayOfNulls<String>(response!!.size)
        val occupied = arrayOfNulls<Boolean>(response!!.size)
        for(i in 0 until response!!.size) {
          cities[i] = (response[i].volunteerCity.city)
          type[i] = (response[i].volunteerType.type)
          occupied[i] = (response[i].isOccupied)
        }

        try {
          val myListAdapter = MyListAdapter(requireActivity(), type, cities, occupied)

          listView?.post {
            listView.adapter = myListAdapter
          }
        } catch(e: Exception) {
          print(e)
        }
      }//else{
      //Log.i("LOG - failed to get activities ", error.toString())
   // }
    }
  }
}