package com.ivolunteer.ivolunteer.ui.volunteerUserFragments.volunteerUserMyVolunteers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ivolunteer.ivolunteer.R
import com.ivolunteer.ivolunteer.ui.needHelpUserFragments.needHelpUserMyActivities.SearchForVolunteersViewModel

class MyVolunteersFragment : Fragment() {

  private lateinit var galleryViewModel: SearchForVolunteersViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    galleryViewModel =
      ViewModelProviders.of(this).get(SearchForVolunteersViewModel::class.java)
    val root = inflater.inflate(R.layout.fragment_my_volunteers, container, false)



    return root
  }
}