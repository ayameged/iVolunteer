package com.ivolunteer.ivolunteer.ui.needHelpUserFragments.needHelpUserNewHelpEvent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ivolunteer.ivolunteer.R
import com.ivolunteer.ivolunteer.resources.NetworkManager
import com.ivolunteer.ivolunteer.types.volunteers.VolunteersTypeItem

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
    var volunteersTypesSpinner = root.findViewById<Spinner>(R.id.events_spinner)

    NetworkManager.instance.get<List<VolunteersTypeItem>>("VolunteerTypes") { response, statusCode, error ->
      if (statusCode == 200) {
        if (response != null) {
          for(item in response){
            volunteers_types.add(item.type)
            }
        }
        volunteersTypesSpinner?.post {
          volunteersTypesSpinner.adapter = activity?.applicationContext?.let { ArrayAdapter(it, R.layout.support_simple_spinner_dropdown_item, volunteers_types) }
//          volunteersTypesSpinner.setSelection(volunteers_types.indexOf(city.toString()))
        }

      }

    }
    return root
  }
}