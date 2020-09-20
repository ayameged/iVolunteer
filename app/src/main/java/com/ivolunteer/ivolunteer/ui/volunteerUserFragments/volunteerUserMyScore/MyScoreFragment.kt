package com.ivolunteer.ivolunteer.ui.volunteerUserFragments.volunteerUserMyScore

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ivolunteer.ivolunteer.R
import com.ivolunteer.ivolunteer.resources.NetworkManager
import com.ivolunteer.ivolunteer.resources.StorageManager
import com.ivolunteer.ivolunteer.resources.StorageTypes
import com.ivolunteer.ivolunteer.types.volunteeruser.VolunteerUser
import com.ivolunteer.ivolunteer.ui.needHelpUserFragments.needHelpUserMyActivities.SearchForVolunteersViewModel

class MyScoreFragment : Fragment() {

  private lateinit var galleryViewModel: MyScoreViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    galleryViewModel = ViewModelProviders.of(this).get(MyScoreViewModel::class.java)
    val root = inflater.inflate(R.layout.fragment_my_score, container, false)
    val ratesNumberText = root.findViewById<TextView>(R.id.rates_number_text)
    val ratingBar = root.findViewById<RatingBar>(R.id.volunteer_rating_bar)


    NetworkManager.instance.get<VolunteerUser>("VolunteerUsers/byid?id="
            +StorageManager.instance.get<String>(StorageTypes.USER_ID.toString())) { response, statusCode, error ->
      if(statusCode == 200){
        var rate = response?.rate?.avgRate
        var ratesNumber = response?.rate?.numberOfRaters
        if (rate != null) ratingBar.rating = rate.toFloat();
        ratesNumberText.post {
          ratesNumberText.text =
            Editable.Factory.getInstance().newEditable("Number of rates: $ratesNumber")
        }

      }else{
        Log.i("LOG - failed to return user", error.toString())
      }
    }
    return root
  }
}