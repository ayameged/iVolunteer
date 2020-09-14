package com.ivolunteer.ivolunteer.ui.needHelpUserFragments.needHelpUserMyActivities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.webkit.WebView
import android.widget.TextView
import com.ivolunteer.ivolunteer.R
import com.ivolunteer.ivolunteer.resources.NetworkManager
import com.ivolunteer.ivolunteer.resources.StorageManager
import com.ivolunteer.ivolunteer.resources.StorageTypes
import com.ivolunteer.ivolunteer.types.VolunteerWithSched.searchedVolunteerItem
import com.ivolunteer.ivolunteer.types.needhelpuseractivities.NeedHelpUserActivities
import com.ivolunteer.ivolunteer.types.needhelpuseractivities.NeedHelpUserActivitiesItem
import com.ivolunteer.ivolunteer.types.volunteeruseractivities.Volunteer
import kotlinx.android.synthetic.main.nav_header_main.*

class NeedHelpDetailsActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    companion object {
        const val EXTRA_TITLE = "title"
        const val EXTRA_URL = "url"

        fun newIntent(context: Context, recipe: NeedHelpUserActivitiesItem): Intent {
            val detailIntent = Intent(context, NeedHelpDetailsActivity::class.java)

            detailIntent.putExtra(EXTRA_TITLE, recipe.volunteerType.type)
            detailIntent.putExtra(EXTRA_URL, recipe.volunteerCity.city)

            return detailIntent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_need_help__details)

        // 1
        val title = intent.extras?.getString(EXTRA_TITLE)
        val url = intent.extras?.getString(EXTRA_URL)

// 2
        setTitle(title)

// 3


        val volunteerId = intent.getStringExtra(EXTRA_MESSAGE)


        //storageManager + message



/*

        NetworkManager.instance.get<searchedVolunteerItem>("volunteers/byid?id=" + volunteerId) { response, statusCode, error ->
            if (statusCode == 200) {

                val test11 = response?.volunteerCity?.city
                val type = response?.volunteerType?.type
                val detailes = response?.details


                // Capture the layout's TextView and set the string as its text
               // val textView = findViewById<TextView>(R.id.detail_web_view).apply {
                  //  text = test11
            }
        }
*/

        val textView = findViewById<TextView>(R.id.detail_web_view).apply {
            text = volunteerId
        }

        // 3
        //  webView = findViewById(R.id.detail_web_view)

// 4
        //if (url != null) {
        //  webView.loadUrl(url)
        //}

// 4
    }
}