package com.ivolunteer.ivolunteer.ui.needHelpUserFragments.needHelpUserMyActivities

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
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
    private val volUserId: Array<String?>,


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







        val updateButtonNeedHelpUser = rowView.findViewById<Button>(R.id.volunteer_detail_contact_button)
        val volunteerDetailsRateButton = rowView.findViewById<Button>(R.id.volunteer_detail_rate_button)

        updateButtonNeedHelpUser.setOnClickListener(object : View.OnClickListener {
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

        //Aya
        volunteerDetailsRateButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //Aya
                StorageManager.instance.set(StorageTypes.USER_ID_RATE_VOLUNTEER.toString(), volUserId)
                volUserName[position]?.let {
                    StorageManager.instance.set(StorageTypes.USER_NAME_RATE_VOLUNTEER.toString(),
                        it
                    )
                }
                volUserEmail[position]?.let {
                    StorageManager.instance.set(StorageTypes.EMAIL_RATE_VOLUNTEER.toString(),
                        it
                    )
                }
                volUserPhoneNumber[position]?.let {
                    StorageManager.instance.set(StorageTypes.PHONE_RATE_VOLUNTEER.toString(),
                        it
                    )
                }
//                var mainFragment: RateVolunteerFragment = RateVolunteerFragment()
//                supportFragmentManager.beginTransaction().add(R.id.container, mainFragment)
//                    .commit()
//                //TODO: start fragment fragment_rate_volunteer
            }})


            return rowView
    }

}

