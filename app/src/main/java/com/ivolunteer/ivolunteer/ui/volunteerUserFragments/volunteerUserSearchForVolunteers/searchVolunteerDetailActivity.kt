/*
package com.ivolunteer.ivolunteer.ui.volunteerUserFragments.volunteerUserSearchForVolunteers

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.View
import android.webkit.WebView
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.ivolunteer.ivolunteer.R
import com.ivolunteer.ivolunteer.resources.NetworkManager
import com.ivolunteer.ivolunteer.types.VolunteerWithSched.searchedVolunteerItem
import com.ivolunteer.ivolunteer.types.VolunteerWithSched.volunteerwithvolUser
import com.ivolunteer.ivolunteer.types.needhelpuseractivities.NeedHelpUserActivitiesItem

class searchVolunteerDetailActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_volunteer__details)
        val volunteerId = intent.getStringExtra(EXTRA_MESSAGE)

        NetworkManager.instance.get<volunteerwithvolUser>("volunteers/byid?id=" + volunteerId) { response, statusCode, error ->
            if (statusCode == 200) {

                val city = response?.volunteerCity?.city
                val type = response?.volunteerType?.type
                val details = response?.details.toString()
                val volunteerSchedulerMorning = response?.volunteerScheduler?.isMorning
                val volunteerSchedulerNoon = response?.volunteerScheduler?.isNoon
                val volunteerSchedulerEvening = response?.volunteerScheduler?.isEvening
                val volunteerSchedulerDays = response?.volunteerScheduler?.weekDays
                val checkBoxMorning = findViewById<CheckBox>(R.id.search_detail_morning_check_box)
                val checkBoxNoon = findViewById<CheckBox>(R.id.search_detail_noon_check_box)
                val checkBoxEvening = findViewById<CheckBox>(R.id.search_detail_evening_check_box)

                val days= arrayOf<CheckBox>(findViewById<CheckBox>(R.id.search_detail_sunday_check_box), findViewById<CheckBox>(R.id.search_detail_monday_check_box),
                    findViewById<CheckBox>(R.id.search_detail_tuesday_check_box), findViewById<CheckBox>(R.id.search_detail_wednesday_check_box),
                    findViewById<CheckBox>(R.id.search_detail_thursday_check_box), findViewById<CheckBox>(R.id.search_detail_friday_check_box),
                    findViewById<CheckBox>(R.id.search_detail_saturday_check_box))

                val textType = findViewById<TextView>(R.id.search_detail_type)
                textType.post {
                    textType.text = type
                }


                val textCity = findViewById<TextView>(R.id.search_detail_city)
                textCity.post {
                    textCity.text = city
                }


                val textDetail = findViewById<TextView>(R.id.search_detail_details)
                textDetail.post {
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
}*/