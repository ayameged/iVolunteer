package com.ivolunteer.ivolunteer.util

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ivolunteer.ivolunteer.InitMainActivity


class MyFirebaseMessagingService : FirebaseMessagingService() {
    val TAG = String::class.java.simpleName

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Send your token here
    }

    fun onTokenRefresh() {
        // Get updated InstanceID token.
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d(TAG, "Refreshed token: $refreshedToken")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
    }
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.notification != null) {
            val title : String = remoteMessage.notification?.title!!
            val body : String = remoteMessage.notification?.body!!
            addNotification(title, body)
        }
    }

    private fun addNotification(title: String, body: String) {
        val builder: Notification.Builder? = Notification.Builder(this)
            //  .setSmallIcon(R.drawable.user_icon) //set icon for notification
            .setContentTitle(title) //set title of notification
            .setContentText(body) //this is notification message
            .setAutoCancel(true) // makes auto cancel of notification
        // .setPriority(NotificationCompat.PRIORITY_DEFAULT) //set priority of notification
        val notificationIntent = Intent(this, InitMainActivity::class.java)
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        //notification message will get at NotificationView
        notificationIntent.putExtra("message", body)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        builder?.setContentIntent(pendingIntent)
        // Add as notification
        val manager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(0, builder?.build())
    }


}

