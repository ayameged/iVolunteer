package com.ivolunteer.ivolunteer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ivolunteer.ivolunteer.R
import com.ivolunteer.ivolunteer.resources.NetworkManager
import com.ivolunteer.ivolunteer.resources.StorageManager
import com.ivolunteer.ivolunteer.resources.StorageTypes
import com.ivolunteer.ivolunteer.types.needhelpuser.NeedHelpUser
import kotlinx.android.synthetic.main.fragment_edit_personal_details.*

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
    val textView: TextView = root.findViewById(R.id.text_home)
    editPersonalDetailsViewModel.text.observe(viewLifecycleOwner, Observer {
      textView.text = it
    })

    NetworkManager.instance.get<NeedHelpUser>("NeedHelpUsers/byid?id="+StorageManager.instance.get<String>(StorageTypes.USER_ID.toString())){response, statusCode, error ->
      if (statusCode==200){
        var firstName = response?.applicationUser?.firstName
        var lastName = response?.applicationUser?.lastName
        var phone = response?.applicationUser?.phoneNumber
        var gender = response?.applicationUser?.gender
        var age = response?.applicationUser?.age
        var city = response?.needHelpCity?.city



      }

    }
    return root
  }
}