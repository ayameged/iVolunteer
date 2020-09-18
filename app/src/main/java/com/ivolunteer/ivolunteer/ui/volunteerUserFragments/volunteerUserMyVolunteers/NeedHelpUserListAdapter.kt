package com.ivolunteer.ivolunteer.ui.volunteerUserFragments.volunteerUserMyVolunteers

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.ivolunteer.ivolunteer.R
import com.ivolunteer.ivolunteer.resources.NetworkManager
import com.ivolunteer.ivolunteer.resources.StorageManager
import com.ivolunteer.ivolunteer.resources.StorageTypes
import com.ivolunteer.ivolunteer.types.Auth
import org.json.JSONObject


class NeedHelpUserListAdapter(
    private val context: Activity,
    private val needHelpUserName: Array<String?>,
    private val needHelpUserEmail: Array<String?>,
    private val needHelpPhoneNumber: Array<String?>,
    private val needHelpUserId: Array<String?>,


    )
    : ArrayAdapter<String>(context, R.layout.custom_needhelp_user_list, needHelpUserId) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_needhelp_user_list, null, true)

        val name = rowView.findViewById(R.id.needhelpUser_name) as TextView
        val email = rowView.findViewById(R.id.needhelpUser_Email) as TextView
        val phoneNumber = rowView.findViewById(R.id.needhelpUser_PhoneNumber) as TextView



        name.post {
            name.text = needHelpUserName[position]
        }


        email.post {
            email.text = needHelpUserEmail[position]
        }
        phoneNumber.post {
            phoneNumber.text = needHelpPhoneNumber[position]
        }










            return rowView
    }

}

