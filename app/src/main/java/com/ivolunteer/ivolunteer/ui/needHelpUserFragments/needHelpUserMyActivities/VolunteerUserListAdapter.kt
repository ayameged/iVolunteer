package com.ivolunteer.ivolunteer.ui.needHelpUserFragments.needHelpUserMyActivities

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.ivolunteer.ivolunteer.R


class VolunteerUserListAdapter(
    private val context: Activity,
    private val volUserName: Array<String?>,
    private val volUserEmail: Array<String?>,
    private val volUserPhoneNumber: Array<String?>,


)
    : ArrayAdapter<String>(context, R.layout.custom_volunteer_user_list, volUserEmail ) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_volunteer_user_list, null, true)

        val name = rowView.findViewById(R.id.volunteerUser_name) as TextView
        val email = rowView.findViewById(R.id.volunteerUser_Email) as TextView
        val phoneNumber = rowView.findViewById(R.id.volunteerUser_PhoneNumber) as TextView



        name.post {
            name.text = volUserName[position]
        }


        email.post {
            email.text = volUserEmail[position]
        }
        phoneNumber.post {
            phoneNumber.text = volUserPhoneNumber[position]
        }


        return rowView
    }
}