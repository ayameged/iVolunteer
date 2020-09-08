package com.ivolunteer.ivolunteer.ui.needHelpUserMyActivities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ivolunteer.ivolunteer.R
import com.ivolunteer.ivolunteer.resources.NetworkManager
import com.ivolunteer.ivolunteer.resources.StorageManager
import com.ivolunteer.ivolunteer.resources.StorageTypes
import com.ivolunteer.ivolunteer.types.needhelpuseractivities.NeedHelpUserActivities

class MyActivitiesFragment : Fragment() {

  private lateinit var galleryViewModel: GalleryViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    galleryViewModel =
      ViewModelProviders.of(this).get(GalleryViewModel::class.java)
    val root = inflater.inflate(R.layout.fragment_my_activities, container, false)

    var listView = root.findViewById<ListView>(R.id.recipe_list_view)
   // val listView = view?.findViewById(android.R.id.recipe_list_view)

    NetworkManager.instance.get<NeedHelpUserActivities>(
      "volunteers/byuserid?id=" + StorageManager.instance.get<String>(
        StorageTypes.USER_ID.toString()
      )
    ) { response, statusCode, error ->
      if (statusCode == 200){
        //        TODO: Meital - list
//


       // response[0].details
        val cities= arrayOfNulls<String>(response!!.size)
        val type= arrayOfNulls<String>(response!!.size)
        for(i in 0 until response!!.size) {
          cities[i] = (response[i].volunteerCity.city)
          type[i] = (response[i].volunteerType.type)
        }


//custom list - not working -

        try {
          val myListAdapter = MyListAdapter(requireActivity(), type, cities)

          listView?.post {
            listView.adapter = myListAdapter
          }

        } catch(e: Exception) {
          print(e)
        }

        //working

//          val adapter = activity?.applicationContext?.let {ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, type)}
//
//          if (listView != null) {
//           listView.post {
//              listView!!.adapter = adapter
//            }
//          }

        }else{
          Log.i("LOG - failed to get activities ", error.toString())
      }

    }
    return root
  }
}