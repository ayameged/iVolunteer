package com.ivolunteer.ivolunteer.ui.volunteerUserFragments.volunteerUserMyVolunteers

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.ivolunteer.ivolunteer.R


class MyVolunteerListAdapter(
    private val context: Activity,
    private val type: Array<String?>,
    private val city: Array<String?>,
)
    : ArrayAdapter<String>(context, R.layout.custom_my_volunteers_list, type) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_my_volunteers_list, null, true)

        val titleText = rowView.findViewById(R.id.volunteer_type) as TextView
        val subtitleText = rowView.findViewById(R.id.volunteer_city) as TextView


        titleText.post {
            titleText.text = type[position]
        }
        subtitleText.post {
            subtitleText.text = city[position]
        }

        return rowView
    }
}