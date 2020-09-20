package com.ivolunteer.ivolunteer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.util.Log

import android.widget.Button
import android.widget.RemoteViews
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.ivolunteer.ivolunteer.util.NotifyDemoActivity


class InitMainActivity : AppCompatActivity() {
    //declaring variables

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityIntent: Intent
        // go straight to main if a token is stored
//        activityIntent = if (Util.getToken() != null) {
//            Intent(this, MainActivity::class.java)
//        } else {
            activityIntent = Intent(this, LoginActivity::class.java)
//        }

        startActivity(activityIntent)
    finish()




    }
}