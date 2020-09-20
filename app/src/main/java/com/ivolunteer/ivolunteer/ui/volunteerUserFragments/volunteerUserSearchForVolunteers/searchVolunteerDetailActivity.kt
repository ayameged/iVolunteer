package com.ivolunteer.ivolunteer.ui.volunteerUserFragments.volunteerUserSearchForVolunteers

import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.ivolunteer.ivolunteer.R
import com.ivolunteer.ivolunteer.resources.NetworkManager
import com.ivolunteer.ivolunteer.resources.StorageManager
import com.ivolunteer.ivolunteer.resources.StorageTypes
import com.ivolunteer.ivolunteer.types.Auth
import com.ivolunteer.ivolunteer.types.VolunteerWithSched.searchedVolunteerItem
import com.ivolunteer.ivolunteer.types.VolunteerWithSched.volunteerwithvolUser
import com.ivolunteer.ivolunteer.ui.needHelpUserFragments.needHelpUserMyActivities.VolunteerUserListAdapter
import org.json.JSONObject

class searchVolunteerDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_volunteer__details)

        val volunteerId = intent.getStringExtra(AlarmClock.EXTRA_MESSAGE)
        getSupportActionBar()?.setTitle("Volunteer Details");
        NetworkManager.instance.get<volunteerwithvolUser>("volunteers/byid?id=" + volunteerId) { response, statusCode, error ->
            if (statusCode == 200) {

                val city = response?.volunteerCity?.city
                val cityId = response?.volunteerCity?.volunteerCityId
                val type = response?.volunteerType?.type
                val typeId = response?.volunteerType?.volunteerTypeId
                val details = response?.details.toString()
                val volunteerSchedulerId = response?.volunteerScheduler?.volunteerSchedulerId
                val isOccupied= response?.isOccupied
                val volunteerUsers= response?.volunteerUser

                val volunteerSchedulerMorning = response?.volunteerScheduler?.isMorning
                val volunteerSchedulerNoon = response?.volunteerScheduler?.isNoon
                val volunteerSchedulerEvening = response?.volunteerScheduler?.isEvening
                val volunteerSchedulerDays = response?.volunteerScheduler?.weekDays
                val checkBoxMorning = findViewById<CheckBox>(R.id.search_detail_morning_check_box)
                val checkBoxNoon = findViewById<CheckBox>(R.id.search_detail_noon_check_box)
                val checkBoxEvening = findViewById<CheckBox>(R.id.search_detail_evening_check_box)


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


                val days= arrayOf<CheckBox>(findViewById<CheckBox>(R.id.search_detail_sunday_check_box), findViewById<CheckBox>(R.id.search_detail_monday_check_box),
                    findViewById<CheckBox>(R.id.search_detail_tuesday_check_box), findViewById<CheckBox>(R.id.search_detail_wednesday_check_box),
                    findViewById<CheckBox>(R.id.search_detail_thursday_check_box), findViewById<CheckBox>(R.id.search_detail_friday_check_box),
                    findViewById<CheckBox>(R.id.search_detail_saturday_check_box))

/*

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

*/

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
                    textDetail.isEnabled = false
                    if (details=="null")
                    {
                        textDetail.text =""
                    }
                    else {
                        textDetail.text = details
                    }
                }

                val textName = findViewById<TextView>(R.id.search_NeedHelpUserName_text)
                textName.post {
                    textName.text = name[0]
                }

                val textPhone = findViewById<TextView>(R.id.search_NeedHelpUserPhone_text)
                textPhone.post {
                    textPhone.text = phoneNumber[0]
                }

                val textEMail = findViewById<TextView>(R.id.search_NeedHelpUserMail_text)
                textEMail.post {
                    textEMail.text = email[0]
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
                val iVolunteerButton = findViewById<Button>(R.id.search_detail_ivolunteer)
                var flagiVolunteer=0;
                if (volunteerUsers != null) {
                    for (user in volunteerUsers) {
                        if (user.id == StorageManager.instance.get<String>(StorageTypes.USER_ID.toString())) {
                            flagiVolunteer = 1
                        }
                    }
                }
                if (flagiVolunteer == 1) {
                    iVolunteerButton.isEnabled=false;
                }


                val updateButtonNeedHelpUser = findViewById<Button>(R.id.search_detail_contact)

                updateButtonNeedHelpUser.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        //val intent = Intent(context, VolunteerUserListAdapter::class.java)

                        val intent =Intent(Intent.ACTION_SENDTO);
                        intent.setType("text/plain");
                        //intent.setPackage("com.google.android.gm");


                        //emailLauncher.type = "message/rfc822"
                        try {
                            startActivity(intent)
                            //startActivity(emailLauncher)
                        }
                        catch (e: ActivityNotFoundException) {
                            print(e)
                        }

                        // Your code that you want to execute on this button click
                    }
                })



                var flag=0;
                iVolunteerButton.setOnClickListener(object : View.OnClickListener {

                    override fun onClick(v: View?) {

                        if (volunteerUsers != null) {
                            for (user in volunteerUsers) {
                                if (user.id == StorageManager.instance.get<String>(StorageTypes.USER_ID.toString())) {
                                    flag = 1
                                }
                            }
                        }
                        if (flag == 0) {

                            val json_update_volunteer = JSONObject()

                            json_update_volunteer.put("volunteerId", volunteerId)
                            json_update_volunteer.put("needHelpUserId", needHelpUserId[0])
                            json_update_volunteer.put("volunteercityId", cityId)
                            json_update_volunteer.put("volunteerTypeId", typeId)
                            json_update_volunteer.put("isOccupied", 1)
                            json_update_volunteer.put("volunteerSchedulerId", volunteerSchedulerId)
                            json_update_volunteer.put("details", details)

                            val loginError = findViewById<TextView>(R.id.search_error_text_view)

                            // build alert dialog
                            val dialogBuilder = AlertDialog.Builder(this@searchVolunteerDetailActivity)

                            val iVolunteerPositiveClick = { dialog: DialogInterface, which: Int ->
                                //PUT update isOccupied
                                NetworkManager.instance.put<Int>(
                                    "volunteers/" + volunteerId,
                                    json_update_volunteer
                                ) { response,
                                    statusCode, error ->
                                    if (statusCode != 204) {
                                        Log.i(
                                            "LOG - error in update volunteer- volunteer already associated for you",
                                            error.toString()
                                        )
                                        loginError?.post {
                                            loginError.visibility = View.VISIBLE
                                        }
                                    } else {
                                        Log.i("LOG - volunteer association updated", "")
                                        loginError?.post {
                                            //loginError.text = "volunteer association updated"
                                            //loginError.visibility = View.VISIBLE
                                            val successDialogBuilder = AlertDialog.Builder(this@searchVolunteerDetailActivity)
                                            successDialogBuilder.setMessage("Volunteer assigned successfully!")
                                            successDialogBuilder.setTitle("iVolunteer")
                                            runOnUiThread {
                                                val successAlert = successDialogBuilder.create()
                                                successAlert.show()
                                            }
                                            iVolunteerButton.isEnabled = false;
                                        }
                                    }
                                }


                                //POST Associate
                                val Associatejson = JSONObject()
                                Associatejson.put("volunteerId", volunteerId)
                                Associatejson.put(
                                    "Id",
                                    StorageManager.instance.get<String>(StorageTypes.USER_ID.toString())
                                )
                                NetworkManager.instance.post<Auth>(
                                    "VolunteerUser_Volunteer",
                                    Associatejson
                                ) { response, statusCode, error ->
                                    if (statusCode != 200) {
                                        Log.i("LOG - error", error.toString())

                                    } else {
                                        Log.i("LOG - associate volunteer", "SUCCESS")
                                    }
                                }
                            }
                            // set message of alert dialog
                            dialogBuilder.setMessage("Do you want to volunteer in this activity?")
                                .setCancelable(false)
                                .setPositiveButton("Proceed", DialogInterface.OnClickListener(function=iVolunteerPositiveClick)) //{
                                        //dialog, id -> finish()
                                //})
                                .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                                        dialog, id -> dialog.cancel()
                                })
                            runOnUiThread {
                                val alert = dialogBuilder.create()
                                alert.show()
                            }
                        }
                        else {
                            val loginError = findViewById<TextView>(R.id.search_error_text_view)
                            iVolunteerButton.isEnabled=false;
                        }
                    }
                })
            }
        }
    }
}