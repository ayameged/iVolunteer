package com.ivolunteer.ivolunteer.ui.volunteerUserFragments.volunteerUserMyVolunteers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.CheckBox
import android.widget.ListView
import android.widget.TextView
import com.ivolunteer.ivolunteer.R
import com.ivolunteer.ivolunteer.resources.NetworkManager
import com.ivolunteer.ivolunteer.types.VolunteerWithSched.searchedVolunteerItem
import com.ivolunteer.ivolunteer.types.VolunteerWithSched.volunteerwithvolUser
import com.ivolunteer.ivolunteer.ui.needHelpUserFragments.needHelpUserMyActivities.VolunteerUserListAdapter

class VolunteerUserDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_volunteer_user_details)

        val volunteerId = intent.getStringExtra(AlarmClock.EXTRA_MESSAGE)

        NetworkManager.instance.get<volunteerwithvolUser>("volunteers/byid?id=" + volunteerId) { response, statusCode, error ->
            if (statusCode == 200) {

                val city = response?.volunteerCity?.city
                val type = response?.volunteerType?.type
                val details = response?.details.toString()
                val volunteerSchedulerMorning = response?.volunteerScheduler?.isMorning
                val volunteerSchedulerNoon = response?.volunteerScheduler?.isNoon
                val volunteerSchedulerEvening = response?.volunteerScheduler?.isEvening
                val volunteerSchedulerDays = response?.volunteerScheduler?.weekDays
                val checkBoxMorning = findViewById<CheckBox>(R.id.my_volunteers_detail_morning_check_box)
                val checkBoxNoon = findViewById<CheckBox>(R.id.my_volunteers_detail_noon_check_box)
                val checkBoxEvening = findViewById<CheckBox>(R.id.my_volunteers_detail_evening_check_box)


                val firstName = arrayOfNulls<String>(1)
                val lastName = arrayOfNulls<String>(1)
                val email = arrayOfNulls<String>(1)
                val phoneNumber = arrayOfNulls<String>(1)
                val needHelpUserId = arrayOfNulls<String>(1)

                val name = arrayOfNulls<String>(2)


                    firstName[0] = (response!!.needHelpUser.applicationUser.firstName)
                    lastName[0] = (response!!.needHelpUser.applicationUser.lastName)
                    email[0] = (response!!.needHelpUser.applicationUser.email)
                    phoneNumber[0] = (response!!.needHelpUser.applicationUser.phoneNumber)
                needHelpUserId[0] = (response!!.needHelpUser.applicationUser.id)

                name[0] = firstName[0] + " " + lastName[0]


                val days= arrayOf<CheckBox>(findViewById<CheckBox>(R.id.my_volunteers_detail_sunday_check_box), findViewById<CheckBox>(R.id.my_volunteers_detail_monday_check_box),
                    findViewById<CheckBox>(R.id.my_volunteers_detail_tuesday_check_box), findViewById<CheckBox>(R.id.my_volunteers_detail_wednesday_check_box),
                    findViewById<CheckBox>(R.id.my_volunteers_detail_thursday_check_box), findViewById<CheckBox>(R.id.my_volunteers_detail_friday_check_box),
                    findViewById<CheckBox>(R.id.my_volunteers_detail_saturday_check_box))



                var listView = findViewById<ListView>(R.id.volunteer_needhelpusers_list)
                try {
                    val myListAdapter = NeedHelpUserListAdapter(this, name, email, phoneNumber, needHelpUserId)


                    runOnUiThread {

                        listView?.post {
                            listView.adapter = myListAdapter
                        }
                    }

                } catch (e: Exception) {
                    print(e)
                }



                val textType = findViewById<TextView>(R.id.my_volunteers_detail_type)
                textType.post {
                    textType.text = type
                }


                val textCity = findViewById<TextView>(R.id.my_volunteers_detail_city)
                textCity.post {
                    textCity.text = city
                }


                val textDetail = findViewById<TextView>(R.id.my_volunteers_detail_details)

                textDetail.post {
                    textDetail.isEnabled = false
                    if (details=="null")
                    {
                        textDetail.text =""
                    }
                    else {
                        textDetail.text = details
                    }
                }


                if (volunteerSchedulerMorning!!) {
                    checkBoxMorning.post {
                        checkBoxMorning.isChecked = true
                    }
                }
                checkBoxMorning.post {
                    checkBoxMorning.isEnabled = false
                }

                if (volunteerSchedulerNoon!!) {
                    checkBoxNoon.post {
                        checkBoxNoon.isChecked = true
                    }
                }
                checkBoxNoon.post {
                    checkBoxNoon.isEnabled = false
                }

                if (volunteerSchedulerEvening!!) {
                    checkBoxEvening.post {
                        checkBoxEvening.isChecked = true
                    }
                }
                checkBoxEvening.post {
                    checkBoxEvening.isEnabled = false
                }

                var i=1;
                if (volunteerSchedulerDays != null) {
                    for (day in volunteerSchedulerDays) {
                        for (i in 1 until 8) {
                            days[i - 1].isEnabled = false
                            if (day==i) {
                                days[i - 1].post {
                                    days[i - 1].isChecked = true
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}