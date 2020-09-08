package com.ivolunteer.ivolunteer.ui.needHelpUserMyActivities

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.ivolunteer.ivolunteer.R

class MyListAdapter(private val context: Activity, private val type: Array<String?>, private val city: Array<String?>)
    : ArrayAdapter<String>(context, R.layout.custom_list, type) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_list, null, true)

        val titleText = rowView.findViewById(R.id.type) as TextView
        val subtitleText = rowView.findViewById(R.id.city) as TextView


        titleText.post {
            titleText.text = type[position]
        }
        subtitleText.post {
            subtitleText.text = city[position]
        }

        return rowView
    }
}