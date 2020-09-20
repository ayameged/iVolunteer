package com.ivolunteer.ivolunteer.ui.needHelpUserFragments.needHelpUserMyActivities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.ivolunteer.ivolunteer.NeedHelpActivity
import com.ivolunteer.ivolunteer.R
import com.ivolunteer.ivolunteer.resources.NetworkManager
import org.json.JSONObject

class RateVolunteer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate_volunteer)
        val volunteerId = intent.getStringExtra("user_id")
        val volunteerPhone = intent.getStringExtra("volunteer_phone")
        val volunteerEmail = intent.getStringExtra("volunteer_email")
        val volunteerUserName = intent.getStringExtra("volunteer_user_name")
        val volunteerRateId = intent.getIntExtra("volunteer_rate_id", 1)

        val userNameTxt = findViewById<TextView>(R.id.volunteerUser_name_rate_page)
        val emailTxt = findViewById<TextView>(R.id.volunteerUser_Email_rate_page)
        val phoneTxt = findViewById<TextView>(R.id.volunteerUser_PhoneNumber_rate_page)

        userNameTxt.text = volunteerUserName
        emailTxt.text = volunteerEmail
        phoneTxt.text = volunteerPhone
    }
    override fun onStart() {
        super.onStart()
        Log.i("LOG - on start" , "")

        val volunteerId = intent.getStringExtra("user_id")
        val volunteerRateId = intent.getIntExtra("volunteer_rate_id", 1)

        val ratingBar = findViewById<RatingBar>(R.id.rate_volunteer_ratingBar)
        val rateButton = findViewById<Button>(R.id.rate_btn)
        rateButton.setOnClickListener {
            val rating = ratingBar.rating
            val json_rate = JSONObject()
            json_rate.put("id", volunteerId)
            json_rate.put("RateId", volunteerRateId)
            json_rate.put("AvgRate", rating)

            NetworkManager.instance.put<Int>("VolunteerUsers/rate?id="+volunteerId, json_rate) { response, statusCode, error ->
                if(statusCode == 204){
                    val successDialogBuilder = AlertDialog.Builder(this)
                    successDialogBuilder.setMessage("Volunteer rate successfully!")
                    successDialogBuilder.setTitle("iVolunteer")
                    runOnUiThread {
                        val successAlert = successDialogBuilder.create()
                        successAlert.show()
                    }
                    val rateIntent = Intent(this,NeedHelpActivity::class.java)
                    startActivity(rateIntent)

                }else{
                    Log.i("LOG - failed to rate volunteer: " , error.toString())
                }
            }
        }

    }
}