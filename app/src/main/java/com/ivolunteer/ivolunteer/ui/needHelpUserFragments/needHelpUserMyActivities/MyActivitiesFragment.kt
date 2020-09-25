package com.ivolunteer.ivolunteer.ui.needHelpUserFragments.needHelpUserMyActivities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.ivolunteer.ivolunteer.NeedHelpActivity
import com.ivolunteer.ivolunteer.R
import com.ivolunteer.ivolunteer.resources.NetworkManager
import com.ivolunteer.ivolunteer.resources.StorageManager
import com.ivolunteer.ivolunteer.resources.StorageTypes
import com.ivolunteer.ivolunteer.types.needhelpuseractivities.NeedHelpUserActivities
import com.ivolunteer.ivolunteer.ui.needHelpUserFragments.needHelpUserNewHelpEvent.NewHelpEventFragment
import com.ivolunteer.ivolunteer.util.NotifyDemoActivity

class MyActivitiesFragment : Fragment() {

  private lateinit var galleryViewModel: SearchForVolunteersViewModel

  @SuppressLint("ResourceType")
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    galleryViewModel =
      ViewModelProviders.of(this).get(SearchForVolunteersViewModel::class.java)
    val root = inflater.inflate(R.layout.fragment_my_activities, container, false)

    var listView = root.findViewById<ListView>(R.id.recipe_list_view)

      NetworkManager.instance.get<NeedHelpUserActivities>("volunteers/byuserid?id=" + StorageManager.instance.get<String>(StorageTypes.USER_ID.toString())) { response, statusCode, error ->
      if (statusCode == 200){

        if (response!!.size ==0){
          val navController =  this.view?.let {Navigation.findNavController(it)}
          this.view?.post {
            navController?.navigate(R.id.action_nav_my_activities_to_nav_new_help_event)
          }
        }

        val cities= arrayOfNulls<String>(response.size)
        val type= arrayOfNulls<String>(response.size)
        val occupied = arrayOfNulls<Boolean>(response.size)
        val volunteerId = arrayOfNulls<Int>(response.size)

        for(i in 0 until response!!.size) {
          cities[i] = (response[i].volunteerCity.city)
          type[i] = (response[i].volunteerType.type)
          occupied[i] = (response[i].isOccupied)
          volunteerId[i] = (response[i].volunteerId)

        }

        try {
          val myListAdapter = MyListAdapter(requireActivity(), type, cities, occupied, volunteerId)

          listView?.post {
            listView.adapter = myListAdapter

          }


          var builder = NotificationCompat.Builder(requireActivity(), "1")
            .setContentTitle("iVolunteer")
            .setContentText("someone interested helping  you")



            listView.setOnItemClickListener { parent, view, position, id ->
                val selectedActivities = myListAdapter.getItem(position)
//NeedHelpUserActivitiesItem selectedCity=myListAdapter.getItem(position)

              val message = selectedActivities.toString()

                val intent = Intent(requireActivity(), NeedHelpDetailsActivity::class.java).apply {
                  putExtra(EXTRA_MESSAGE, message)
                }
                startActivity(intent)


            }


        } catch(e: Exception) {
          print(e)
        }
      }else{
          Log.i("LOG - failed to get activities ", error.toString())
      }
    }
    return root
  }
}

