package com.ivolunteer.ivolunteer.ui.needHelpUserMyActivities

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.ivolunteer.ivolunteer.R


class MyListAdapter(
    private val context: Activity,
    private val type: Array<String?>,
    private val city: Array<String?>,
    private val occupied: Array<Boolean?>
)
    : ArrayAdapter<String>(context, R.layout.custom_list, type) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_list, null, true)

        val titleText = rowView.findViewById(R.id.type) as TextView
        val subtitleText = rowView.findViewById(R.id.city) as TextView
        val imageCheckBox = rowView.findViewById(R.id.is_occupied_check_box) as ImageView


        titleText.post {
            titleText.text = type[position]
        }
        subtitleText.post {
            subtitleText.text = city[position]
        }

        if(occupied[position]!!){
            imageCheckBox.post {
                imageCheckBox.setImageResource(android.R.drawable.checkbox_on_background);
            }
        } else{
            imageCheckBox.post {
                imageCheckBox.setImageResource(android.R.drawable.checkbox_off_background);
            }
        }

        return rowView
    }
}