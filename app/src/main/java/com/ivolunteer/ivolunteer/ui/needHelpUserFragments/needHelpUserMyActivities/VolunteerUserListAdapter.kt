package com.ivolunteer.ivolunteer.ui.needHelpUserFragments.needHelpUserMyActivities

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.ivolunteer.ivolunteer.R
import com.ivolunteer.ivolunteer.resources.StorageManager
import com.ivolunteer.ivolunteer.resources.StorageTypes


class VolunteerUserListAdapter(
    private val context: Activity,
    private val volUserName: Array<String?>,
    private val volUserEmail: Array<String?>,
    private val volUserPhoneNumber: Array<String?>,
    private val VolRateId: Array<Int?>,
    private val volUserId: Array<String?>


    )
    : ArrayAdapter<String>(context, R.layout.custom_volunteer_user_list, volUserId) {

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

        val volunteerDetailsContactButton = rowView.findViewById<Button>(R.id.volunteer_detail_contact_button)
        val volunteerDetailsRateButton = rowView.findViewById<Button>(R.id.volunteer_detail_rate_button)

        volunteerDetailsContactButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //val intent = Intent(context, VolunteerUserListAdapter::class.java)

                 val intent =Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",volUserEmail[position], null));
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL,volUserEmail[position])
                intent.putExtra(Intent.EXTRA_SUBJECT,"How can you help me?")

                //emailLauncher.type = "message/rfc822"
                try {
                    context.startActivity(Intent.createChooser(intent,"Send email"))
                    //startActivity(emailLauncher)
                }
                catch (e: ActivityNotFoundException) {
                    print(e)
                }

                // Your code that you want to execute on this button click
            }
        })

        volunteerDetailsRateButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                try {
                    val rateIntent = Intent(context,RateVolunteer::class.java)
                    rateIntent.putExtra("user_id", volUserId[position])
                    rateIntent.putExtra("volunteer_user_name", volUserName[position])
                    rateIntent.putExtra("volunteer_email", volUserEmail[position])
                    rateIntent.putExtra("volunteer_phone", volUserPhoneNumber[position])
                    rateIntent.putExtra("volunteer_rate_id", VolRateId[position])
                    context.startActivity(rateIntent)


                } catch (E: Exception) {
                    Log.i("error", E.toString())
                }

            }})


            return rowView
    }

}

