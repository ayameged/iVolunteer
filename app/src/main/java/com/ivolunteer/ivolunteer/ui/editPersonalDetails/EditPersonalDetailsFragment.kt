package com.ivolunteer.ivolunteer.ui.editPersonalDetails

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ivolunteer.ivolunteer.R
import com.ivolunteer.ivolunteer.UserTypes
import com.ivolunteer.ivolunteer.resources.NetworkManager
import com.ivolunteer.ivolunteer.resources.StorageManager
import com.ivolunteer.ivolunteer.resources.StorageTypes
import com.ivolunteer.ivolunteer.types.City
import com.ivolunteer.ivolunteer.types.needhelpuser.NeedHelpUser
import com.ivolunteer.ivolunteer.types.volunteeruser.VolunteerUser
import com.ivolunteer.ivolunteer.util.Helper
import kotlinx.android.synthetic.main.fragment_edit_personal_details.*
import org.json.JSONObject

class EditPersonalDetailsFragment : Fragment() {

  private lateinit var editPersonalDetailsViewModel: EditPersonalDetailsModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    editPersonalDetailsViewModel =
      ViewModelProviders.of(this).get(EditPersonalDetailsModel::class.java)
    val root = inflater.inflate(R.layout.fragment_edit_personal_details, container, false)
//    val textView: TextView = root.findViewById(R.id.text_home)
//    editPersonalDetailsViewModel.text.observe(viewLifecycleOwner, Observer {
//      textView.text = it
//    })

//    val genderSelection = arrayOf("Male", "Female")
//    var cities_names: ArrayList<String> = ArrayList()
//    var citySpinner = view?.findViewById<Spinner>(R.id.city_spinner_update)
//    val genderSpinner = view?.findViewById<Spinner>(R.id.gender_spinner_update)

    if (StorageManager.instance.get<String>(StorageTypes.USER_TYPE.toString()) == UserTypes.NeedHelpUser.name) {
      //Get need help user details
      NetworkManager.instance.get<NeedHelpUser>("NeedHelpUsers/byid?id=" + StorageManager.instance.get<String>(StorageTypes.USER_ID.toString())) { response, statusCode, error ->
        if (statusCode == 200) {
          var firstName = response?.applicationUser?.firstName
          var lastName = response?.applicationUser?.lastName
          var phone = response?.applicationUser?.phoneNumber
          var gender = response?.applicationUser?.gender
          var age = response?.applicationUser?.age
          var city = response?.needHelpCity?.city


          editText(R.id.first_name_update_text, firstName.toString())
          editText(R.id.last_name_text_update, lastName.toString())
          editText(R.id.phone_text_update, phone.toString())
          editText(R.id.age_text_update, age.toString())

          val genderSelection = arrayOf("Male", "Female")
          var cities_names: ArrayList<String> = ArrayList()

          var citySpinner = view?.findViewById<Spinner>(R.id.city_spinner_update)
          val genderSpinner = view?.findViewById<Spinner>(R.id.gender_spinner_update)


          genderSpinner?.post {
            genderSpinner?.adapter = activity?.applicationContext?.let {ArrayAdapter(it, R.layout.support_simple_spinner_dropdown_item,genderSelection)}
            genderSpinner?.setSelection(genderSelection.indexOf(gender.toString()))
          }
          NetworkManager.instance.get<List<City>>("NeedHelpCities") { response, statusCode, error ->
            if (response != null) {
              for (city in response) {
                cities_names.add(city.city)
              }
              StorageManager.instance.set(StorageTypes.CITIES_LIST.toString(), response)

              citySpinner?.post {
                citySpinner.adapter = activity?.applicationContext?.let {ArrayAdapter(it, R.layout.support_simple_spinner_dropdown_item, cities_names)}
                citySpinner.setSelection(cities_names.indexOf(city.toString()))
              }
            }
          }
        }
      }
    }
    else if (StorageManager.instance.get<String>(StorageTypes.USER_TYPE.toString()) == UserTypes.VolunteerUser.name) {
      NetworkManager.instance.get<VolunteerUser>("VolunteerUsers/byid?id=" + StorageManager.instance.get<String>(StorageTypes.USER_ID.toString())) { response, statusCode, error ->
        if (statusCode == 200){
          var firstName = response?.applicationUser?.firstName
          var lastName = response?.applicationUser?.lastName
          var phone = response?.applicationUser?.phoneNumber
          var gender = response?.applicationUser?.gender
          var age = response?.applicationUser?.age
          var city = response?.volunteerUserCity?.city

          editText(R.id.first_name_update_text, firstName.toString())
          editText(R.id.last_name_text_update, lastName.toString())
          editText(R.id.phone_text_update, phone.toString())
          editText(R.id.age_text_update, age.toString())

          val genderSelection = arrayOf("Male", "Female")
          var cities_names: ArrayList<String> = ArrayList()

          var citySpinner = view?.findViewById<Spinner>(R.id.city_spinner_update)
          val genderSpinner = view?.findViewById<Spinner>(R.id.gender_spinner_update)

          genderSpinner?.post {
            genderSpinner?.adapter = activity?.applicationContext?.let {
              ArrayAdapter(it, R.layout.support_simple_spinner_dropdown_item,genderSelection)
            }
            genderSpinner?.setSelection(genderSelection.indexOf(gender.toString()))
          }

          NetworkManager.instance.get<List<City>>("NeedHelpCities") { response, statusCode, error ->
            if (response != null) {
              for (city in response) {
                cities_names.add(city.city)
              }
              StorageManager.instance.set(StorageTypes.CITIES_LIST.toString(), response)

              citySpinner?.post {
                citySpinner.adapter = activity?.applicationContext?.let {ArrayAdapter(it, R.layout.support_simple_spinner_dropdown_item, cities_names)}
                citySpinner.setSelection(cities_names.indexOf(city.toString()))
              }
            }
          }
        }

      }
    }
    return root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val citySpinner = view.findViewById<Spinner>(R.id.city_spinner_update)
    val genderSpinner = view.findViewById<Spinner>(R.id.gender_spinner_update)
    val firstName = view.findViewById<EditText>(R.id.first_name_update_text)
    val lastName = view.findViewById<EditText>(R.id.last_name_text_update)
    val phone = view.findViewById<EditText>(R.id.phone_text_update)
    val age = view.findViewById<EditText>(R.id.age_text_update)


    update_btn_need_help_user.setOnClickListener {

      var cityId = Helper.getCityId(citySpinner.selectedItem.toString())
      val json_update_user = JSONObject()
      json_update_user.put("id", StorageManager.instance.get<String>(StorageTypes.USER_ID.toString()))

      val json_application_user = JSONObject()
      json_application_user.put("firstname", firstName.text)
      json_application_user.put("lastName", lastName.text)
      json_application_user.put("age", age.text)
      json_application_user.put("Gender", genderSpinner.selectedItem)
      json_application_user.put("phoneNumber", phone.text)
      json_update_user.put("ApplicationUser", json_application_user)
      if (StorageManager.instance.get<String>(StorageTypes.USER_TYPE.toString()) == UserTypes.NeedHelpUser.name) {
        json_update_user.put("needhelpcityid", cityId)
        updateNeedHelpUser(json_update_user)
      }
      else if (StorageManager.instance.get<String>(StorageTypes.USER_TYPE.toString()) == UserTypes.VolunteerUser.name){
        json_update_user.put("rateId", StorageManager.instance.get<Int>(StorageTypes.RATE_ID.toString()))
        json_update_user.put("volunteerusercityid", cityId)
        updateVolunteerUser(json_update_user)
      }
    }
  }

  private fun editText(id: Int, text: String){
    var EditText  = view?.findViewById<EditText>(id)
    if (EditText != null) {
      if (text != "null") {
        EditText.post {
          EditText.text =
            Editable.Factory.getInstance().newEditable(text)
        }
      }
    }
  }

  private fun updateNeedHelpUser(json_update_user: JSONObject){
    NetworkManager.instance.put<Int>("NeedHelpUsers/"+ StorageManager.instance.get<String>(
      StorageTypes.USER_ID.toString()), json_update_user){ response, statusCode, error ->
      if (statusCode != 204){
        Log.i("LOG - error in update user", error.toString())
      }else{
        Log.i("LOG - need help user updated ", "")
      }
    }
  }

  private fun updateVolunteerUser(json_update_user: JSONObject){
    NetworkManager.instance.put<Int>("VolunteerUsers/"+StorageManager.instance.get<String>(StorageTypes.USER_ID.toString()), json_update_user){ response, statusCode, error ->
      if (statusCode != 204){
        Log.i("LOG - error in update user", error.toString())
      }else{
        Log.i("LOG - volunteer user created ", "")
      }
    }
  }
}