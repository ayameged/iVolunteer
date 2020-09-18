package com.ivolunteer.ivolunteer.ui.needHelpUserFragments.needHelpUserMyActivities

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.widget.Button
import android.widget.CheckBox
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ivolunteer.ivolunteer.R
import com.ivolunteer.ivolunteer.resources.NetworkManager
import com.ivolunteer.ivolunteer.types.VolunteerWithSched.volunteerwithvolUser


class NeedHelpDetailsActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_need_help__details)
        val volunteerId = intent.getStringExtra(EXTRA_MESSAGE)

        NetworkManager.instance.get<volunteerwithvolUser>("volunteers/byid?id=" + volunteerId) { response, statusCode, error ->
            if (statusCode == 200) {

                val city = response?.volunteerCity?.city
                val type = response?.volunteerType?.type
                val details = response?.details
                val volunteerSchedulerMorning = response?.volunteerScheduler?.isMorning
                val volunteerSchedulerNoon = response?.volunteerScheduler?.isNoon
                val volunteerSchedulerEvening = response?.volunteerScheduler?.isEvening
                val volunteerSchedulerDays = response?.volunteerScheduler?.weekDays

                val firstName = arrayOfNulls<String>(response!!.volunteerUser.size)
                val lastName = arrayOfNulls<String>(response!!.volunteerUser.size)
                val email = arrayOfNulls<String>(response!!.volunteerUser.size)
                val phoneNumber = arrayOfNulls<String>(response!!.volunteerUser.size)
                val name = arrayOfNulls<String>(response!!.volunteerUser.size)
                val volunteerUserId = arrayOfNulls<String>(response!!.volunteerUser.size)


                for (i in 0 until response.volunteerUser.size) {
                    firstName[i] = (response.volunteerUser[i].applicationUser.firstName)
                    lastName[i] = (response.volunteerUser[i].applicationUser.lastName)
                    email[i] = (response.volunteerUser[i].applicationUser.email)
                    phoneNumber[i] = (response.volunteerUser[i].applicationUser.phoneNumber)
                    volunteerUserId[i] = (response.volunteerUser[i].applicationUser.id)

                    name[i] = firstName[i] + " " + lastName[i]

                }

              //  val needhelp_detail_contact =
                  //  findViewById<Button>(R.id.volunteer_detail_contact_button)
                val checkBoxMorning = findViewById<CheckBox>(R.id.detail_morning_check_box)
                val checkBoxNoon = findViewById<CheckBox>(R.id.detail_noon_check_box)
                val checkBoxEvening = findViewById<CheckBox>(R.id.detail_evening_check_box)

                val days = arrayOf<CheckBox>(
                    findViewById<CheckBox>(R.id.detail_sunday_check_box),
                    findViewById<CheckBox>(
                        R.id.detail_monday_check_box
                    ),
                    findViewById<CheckBox>(R.id.detail_tuesday_check_box),
                    findViewById<CheckBox>(R.id.detail_wednesday_check_box),
                    findViewById<CheckBox>(R.id.detail_thursday_check_box),
                    findViewById<CheckBox>(R.id.detail_friday_check_box),
                    findViewById<CheckBox>(R.id.detail_saturday_check_box)
                )
                var listView = findViewById<ListView>(R.id.volunteer_volunteerUsers_list)
                try {
                    val myListAdapter = VolunteerUserListAdapter(this, name, email, phoneNumber, volunteerUserId)


                    runOnUiThread {

                        listView?.post {
                            listView.adapter = myListAdapter
                        }
                    }

                } catch (e: Exception) {
                    print(e)
                }

                val textType = findViewById<TextView>(R.id.detail_type)
                textType.post {
                    textType.text = type
                }

                val textCity = findViewById<TextView>(R.id.detail_city)
                textCity.post {
                    textCity.text = city
                }

                val textDetail = findViewById<TextView>(R.id.detail_details)
                textDetail.post {
                    if (details=="null") {
                        textDetail.text = ""
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
                textDetail.post {
                    textDetail.isEnabled = false
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

                var i = 1;
                if (volunteerSchedulerDays != null) {
                    for (day in volunteerSchedulerDays) {
                        for (i in 1 until 8) {
                            days[i - 1].isEnabled = false
                            if (day == i) {
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