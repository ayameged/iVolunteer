package com.ivolunteer.ivolunteer.ui.needHelpUserFragments.needHelpUserNewHelpEvent

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ivolunteer.ivolunteer.NeedHelpActivity
import com.ivolunteer.ivolunteer.R
import com.ivolunteer.ivolunteer.resources.NetworkManager
import com.ivolunteer.ivolunteer.resources.StorageManager
import com.ivolunteer.ivolunteer.resources.StorageTypes
import com.ivolunteer.ivolunteer.types.City
import com.ivolunteer.ivolunteer.types.Type
import com.ivolunteer.ivolunteer.types.VolunteerWithSched.volunteerwithvolUser
import com.ivolunteer.ivolunteer.util.Helper
import org.json.JSONObject


enum class Days(val value: Int) {
  SUNDAY(0),
  MONDAY(1),
  TUESDAY(2),
  WEDNESDAY(3),
  THURSDAY(4),
  FRIDAY(5),
  SATURDAY(6)
}


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
          volunteers_types.add("")
          for(item in response){
            volunteers_types.add(item.type)
            }
          StorageManager.instance.set(StorageTypes.TYPES_LIST.toString(), response)
        }
        volunteersTypesSpinner?.post {
          volunteersTypesSpinner.adapter = activity?.applicationContext?.let { ArrayAdapter(
            it,
            R.layout.support_simple_spinner_dropdown_item,
            volunteers_types
          ) }
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
          volunteersCitiesSpinner.adapter = activity?.applicationContext?.let { ArrayAdapter(
            it,
            R.layout.support_simple_spinner_dropdown_item,
            volunteers_cities
          ) }
        }
      }
    }
    return root
  }


  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val createEventButton = view.findViewById<Button>(R.id.create_event_btn)
    val morningCheckBox = view.findViewById<CheckBox>(R.id.morning_check_box)
    val noonCheckBox = view.findViewById<CheckBox>(R.id.noon_check_box)
    val eveningCheckBox = view.findViewById<CheckBox>(R.id.evening_check_box)
    val volunteersCitiesSpinner = view.findViewById<Spinner>(R.id.city_spinner_new_event)
    val volunteersTypesSpinner = view.findViewById<Spinner>(R.id.events_spinner)
    val details = view.findViewById<TextView>(R.id.additinal_details_text)
    val days = arrayOf<CheckBox>(
      view.findViewById<CheckBox>(R.id.sunday_check_box),
      view.findViewById<CheckBox>(
        R.id.monday_check_box
      ),
      view.findViewById<CheckBox>(R.id.tuesday_check_box),
      view.findViewById<CheckBox>(R.id.wednesday_check_box),
      view.findViewById<CheckBox>(R.id.thursday_check_box),
      view.findViewById<CheckBox>(R.id.friday_check_box),
      view.findViewById<CheckBox>(R.id.saturday_check_box)
    )
    var timesFlags = arrayOf<Int>(0, 0, 0)
    var daysFlags = arrayOf<Int>(0, 0, 0, 0, 0, 0, 0)
    var typesSpinnerFlag = 0
    var citiesSpinnerFlag = 0


    volunteersTypesSpinner.setOnItemSelectedListener(object : OnItemSelectedListener {
      override fun onItemSelected(
        parentView: AdapterView<*>?,
        selectedItemView: View,
        position: Int,
        id: Long
      ) {
        if(volunteersTypesSpinner.selectedItem != "") {
          typesSpinnerFlag = 1

          changeCreateButtonStatus(
            daysFlags = daysFlags.toIntArray(),
            timesFlags = timesFlags.toIntArray(),
            typesSpinnerFlag = typesSpinnerFlag,
            citiesSpinnerFlag = citiesSpinnerFlag,
            createButton = createEventButton
          )
        } else {
          createEventButton.isEnabled = false
        }
      }
      override fun onNothingSelected(parentView: AdapterView<*>?) {
        typesSpinnerFlag = 0
      }
    })


    volunteersCitiesSpinner.setOnItemSelectedListener(object : OnItemSelectedListener {
      override fun onItemSelected(
        parentView: AdapterView<*>?,
        selectedItemView: View,
        position: Int,
        id: Long
      ) {
        if(volunteersCitiesSpinner.selectedItem != "") {
          citiesSpinnerFlag = 1

          changeCreateButtonStatus(
            daysFlags = daysFlags.toIntArray(),
            timesFlags = timesFlags.toIntArray(),
            typesSpinnerFlag = typesSpinnerFlag,
            citiesSpinnerFlag = citiesSpinnerFlag,
            createButton = createEventButton
          )
        } else {
          createEventButton.isEnabled = false
        }
      }
      override fun onNothingSelected(parentView: AdapterView<*>?) {
        citiesSpinnerFlag = 0
      }
    })


   // volunteersCitiesSpinner.setOnItemSelectedListener

    morningCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
      if (isChecked) {
        timesFlags[0] = 1
      } else {
        timesFlags[0] = 0
      }
      changeCreateButtonStatus(
        daysFlags = daysFlags.toIntArray(),
        timesFlags = timesFlags.toIntArray(),
        typesSpinnerFlag = typesSpinnerFlag,
        citiesSpinnerFlag = citiesSpinnerFlag,
        createButton = createEventButton
      )
    }

    noonCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
      if (isChecked) {
        timesFlags[1] = 1
      } else {
        timesFlags[1] = 0
      }
      changeCreateButtonStatus(
        daysFlags = daysFlags.toIntArray(),
        timesFlags = timesFlags.toIntArray(),
        typesSpinnerFlag = typesSpinnerFlag,
        citiesSpinnerFlag = citiesSpinnerFlag,
        createButton = createEventButton
      )
    }

    eveningCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
      if (isChecked) {
        timesFlags[2] = 1
      } else {
        timesFlags[2] = 0
      }
      changeCreateButtonStatus(
        daysFlags = daysFlags.toIntArray(),
        timesFlags = timesFlags.toIntArray(),
        typesSpinnerFlag = typesSpinnerFlag,
        citiesSpinnerFlag = citiesSpinnerFlag,
        createButton = createEventButton
      )
    }

    days[Days.SUNDAY.value].setOnCheckedChangeListener { buttonView, isChecked ->
      if (isChecked) {
        daysFlags[Days.SUNDAY.value] = 1
      } else {
        daysFlags[Days.SUNDAY.value] = 0
      }
      changeCreateButtonStatus(
        daysFlags = daysFlags.toIntArray(),
        timesFlags = timesFlags.toIntArray(),
        typesSpinnerFlag = typesSpinnerFlag,
        citiesSpinnerFlag = citiesSpinnerFlag,
        createButton = createEventButton
      )
    }

    days[Days.MONDAY.value].setOnCheckedChangeListener { buttonView, isChecked ->
      if (isChecked) {
        daysFlags[Days.MONDAY.value] = 1
      } else {
        daysFlags[Days.MONDAY.value] = 0
      }
      changeCreateButtonStatus(
        daysFlags = daysFlags.toIntArray(),
        timesFlags = timesFlags.toIntArray(),
        typesSpinnerFlag = typesSpinnerFlag,
        citiesSpinnerFlag = citiesSpinnerFlag,
        createButton = createEventButton
      )
    }

    days[Days.TUESDAY.value].setOnCheckedChangeListener { buttonView, isChecked ->
      if (isChecked) {
        daysFlags[Days.TUESDAY.value] = 1
      } else {
        daysFlags[Days.TUESDAY.value] = 0
      }
      changeCreateButtonStatus(
        daysFlags = daysFlags.toIntArray(),
        timesFlags = timesFlags.toIntArray(),
        typesSpinnerFlag = typesSpinnerFlag,
        citiesSpinnerFlag = citiesSpinnerFlag,
        createButton = createEventButton
      )
    }

    days[Days.WEDNESDAY.value].setOnCheckedChangeListener { buttonView, isChecked ->
      if (isChecked) {
        daysFlags[Days.WEDNESDAY.value] = 1
      } else {
        daysFlags[Days.WEDNESDAY.value] = 0
      }
      changeCreateButtonStatus(
        daysFlags = daysFlags.toIntArray(),
        timesFlags = timesFlags.toIntArray(),
        typesSpinnerFlag = typesSpinnerFlag,
        citiesSpinnerFlag = citiesSpinnerFlag,
        createButton = createEventButton
      )
    }

    days[Days.THURSDAY.value].setOnCheckedChangeListener { buttonView, isChecked ->
      if (isChecked) {
        daysFlags[Days.THURSDAY.value] = 1
      } else {
        daysFlags[Days.THURSDAY.value] = 0
      }
      changeCreateButtonStatus(
        daysFlags = daysFlags.toIntArray(),
        timesFlags = timesFlags.toIntArray(),
        typesSpinnerFlag = typesSpinnerFlag,
        citiesSpinnerFlag = citiesSpinnerFlag,
        createButton = createEventButton
      )
    }

    days[Days.FRIDAY.value].setOnCheckedChangeListener { buttonView, isChecked ->
      if (isChecked) {
        daysFlags[Days.FRIDAY.value] = 1
      } else {
        daysFlags[Days.FRIDAY.value] = 0
      }
      changeCreateButtonStatus(
        daysFlags = daysFlags.toIntArray(),
        timesFlags = timesFlags.toIntArray(),
        typesSpinnerFlag = typesSpinnerFlag,
        citiesSpinnerFlag = citiesSpinnerFlag,
        createButton = createEventButton
      )
    }

    days[Days.SATURDAY.value].setOnCheckedChangeListener { buttonView, isChecked ->
      if (isChecked) {
        daysFlags[Days.SATURDAY.value] = 1
      } else {
        daysFlags[Days.SATURDAY.value] = 0
      }
      changeCreateButtonStatus(
        daysFlags = daysFlags.toIntArray(),
        timesFlags = timesFlags.toIntArray(),
        typesSpinnerFlag = typesSpinnerFlag,
        citiesSpinnerFlag = citiesSpinnerFlag,
        createButton = createEventButton
      )
    }


    createEventButton.setOnClickListener {

      val cityId = Helper.getCityId(volunteersCitiesSpinner.selectedItem.toString())
      val typeId = Helper.getTypeId(volunteersTypesSpinner.selectedItem.toString())

      val internalData: ArrayList<Int> = ArrayList()
      var i = 1;
      for (checkBox in days) {
        if (checkBox.isChecked) {
          internalData.add(i)
        }
        i++;
      }

      val scheduleJson = JSONObject()
      val json = JSONObject()

      scheduleJson.put("isMorning", morningCheckBox.isChecked)
      scheduleJson.put("isNoon", noonCheckBox.isChecked)
      scheduleJson.put("isEvening", eveningCheckBox.isChecked)
      scheduleJson.put("internalData", internalData.joinToString(separator = ";"))
      json.put("volunteerscheduler", scheduleJson)
      json.put(
        "needhelpUserId",
        StorageManager.instance.get<String>(StorageTypes.USER_ID.toString())
      )
      json.put("volunteercityid", cityId)
      json.put("volunteertypeid", typeId)
      json.put("details", details.text)

      NetworkManager.instance.post<volunteerwithvolUser>(
        "volunteers",
        json
      ) { response, statusCode, error ->
        if (statusCode != 201) {
          Log.i("LOG - error", error.toString())
        } else {
          val successDialogBuilder = AlertDialog.Builder(requireActivity())
          successDialogBuilder.setMessage("New help event created successfully!")
          successDialogBuilder.setTitle("iVolunteer")

          requireActivity().runOnUiThread {
            val successAlert = successDialogBuilder.create()
            successAlert.show()

          }
          Thread.sleep(1_000)
          val intent = Intent(activity, NeedHelpActivity::class.java)
          activity?.startActivity(intent)
          Log.i("LOG - login", "SUCCESS")

        }
      }
    }
  }

  private fun changeCreateButtonStatus(daysFlags: IntArray, timesFlags: IntArray, typesSpinnerFlag: Int,
                                       citiesSpinnerFlag: Int, createButton: Button){
    createButton.isEnabled = daysFlags.contains(1) && timesFlags.contains(1) && (typesSpinnerFlag == 1) && (citiesSpinnerFlag ==1)
  }
}