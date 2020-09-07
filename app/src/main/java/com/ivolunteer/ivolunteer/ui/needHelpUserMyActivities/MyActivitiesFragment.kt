package com.ivolunteer.ivolunteer.ui.needHelpUserMyActivities

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ivolunteer.ivolunteer.R
import com.ivolunteer.ivolunteer.resources.NetworkManager
import com.ivolunteer.ivolunteer.resources.StorageManager
import com.ivolunteer.ivolunteer.resources.StorageTypes
import com.ivolunteer.ivolunteer.types.needhelpuseractivities.NeedHelpUserActivities
import com.ivolunteer.ivolunteer.ui.needHelpUserEditPersonalDetails.MyListAdapter

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
        val details=arrayOfNulls<String>(response!!.size)
        val type=arrayOfNulls<String>(response!!.size)
        for(i in 0 until response!!.size) {
          details[i] = (response[i].details)
          type[i] = (response[i].volunteerType.type)
        }


//custom list - not working -
/*
        val myListAdapter =activity?.applicationContext?.let { MyListAdapter(it as Fragment,type,details)}


        if (listView != null) {
          listView.post {


            listView!!.adapter = myListAdapter

          }
        }
*/
        //working

          val adapter = activity?.applicationContext?.let {
            ArrayAdapter<String>(
              it, android.R.layout.simple_list_item_1, type)
          }


        if (listView != null) {
          listView.post {


            listView!!.adapter = adapter

          }
        }





        }else{
        //Log.i("LOG - failed to get activities ", error.toString())
      }

    }
    return root
  }
}