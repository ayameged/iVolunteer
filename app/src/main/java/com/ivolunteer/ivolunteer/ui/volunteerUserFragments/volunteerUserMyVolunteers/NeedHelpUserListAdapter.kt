package com.ivolunteer.ivolunteer.ui.volunteerUserFragments.volunteerUserMyVolunteers

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.ivolunteer.ivolunteer.R


class NeedHelpUserListAdapter(
    private val context: Activity,
    private val needHelpUserName: Array<String?>,
    private val needHelpUserEmail: Array<String?>,
    private val needHelpPhoneNumber: Array<String?>,


    )
    : ArrayAdapter<String>(context, R.layout.custom_needhelp_user_list, needHelpUserEmail) {

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







        val updateButtonNeedHelpUser = rowView.findViewById<Button>(R.id.needhelp_detail_contact_button)

        updateButtonNeedHelpUser.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //val intent = Intent(context, VolunteerUserListAdapter::class.java)

                 val intent =Intent(Intent.ACTION_SENDTO);
                intent.setType("text/plain");

                //emailLauncher.type = "message/rfc822"
                try {
                    context.startActivity(intent)
                    //startActivity(emailLauncher)
                }
                catch (e: ActivityNotFoundException) {
                    print(e)
                }

                // Your code that you want to execute on this button click
            }
        })




            return rowView
    }

}

