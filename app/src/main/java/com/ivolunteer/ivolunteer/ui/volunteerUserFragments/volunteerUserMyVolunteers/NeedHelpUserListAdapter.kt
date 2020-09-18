package com.ivolunteer.ivolunteer.ui.volunteerUserFragments.volunteerUserMyVolunteers

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.ivolunteer.ivolunteer.R
import com.ivolunteer.ivolunteer.resources.NetworkManager
import com.ivolunteer.ivolunteer.resources.StorageManager
import com.ivolunteer.ivolunteer.resources.StorageTypes
import com.ivolunteer.ivolunteer.types.Auth
import org.json.JSONObject


class NeedHelpUserListAdapter(
    private val context: Activity,
    private val needHelpUserName: Array<String?>,
    private val needHelpUserEmail: Array<String?>,
    private val needHelpPhoneNumber: Array<String?>,
    private val needHelpUserId: Array<String?>,


    )
    : ArrayAdapter<String>(context, R.layout.custom_needhelp_user_list, needHelpUserId) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_needhelp_user_list, null, true)

        val name = rowView.findViewById(R.id.needhelpUser_name) as TextView
        val email = rowView.findViewById(R.id.needhelpUser_Email) as TextView
        val phoneNumber = rowView.findViewById(R.id.needhelpUser_PhoneNumber) as TextView



        name.post {
            name.text = needHelpUserName[position]
        }


        email.post {
            email.text = needHelpUserEmail[position]
        }
        phoneNumber.post {
            phoneNumber.text = needHelpPhoneNumber[position]
        }







        val updateButtonNeedHelpUser = rowView.findViewById<Button>(R.id.needhelp_detail_contact_button)

        updateButtonNeedHelpUser.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //val intent = Intent(context, VolunteerUserListAdapter::class.java)

                 val intent =Intent(Intent.ACTION_SENDTO);
                intent.setType("text/plain");

                //emailLauncher.type = "message/rfc822"
                try {
                    context.startActivity(intent)
                    //startActivity(emailLauncher)
                }
                catch (e: ActivityNotFoundException) {
                    print(e)
                }

                // Your code that you want to execute on this button click
            }
        })

        val iVolunteerButton = rowView.findViewById<Button>(R.id.needhelp_detail_iVolunteer_button)

        iVolunteerButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                //POST Associate

                val Associatejson = JSONObject()


                Associatejson.put("volunteerId",28 )
                Associatejson.put("Id", "07bba5be-1e94-47fc-b4ca-28de88dd8cfd")
                NetworkManager.instance.post<Auth>("VolunteerUser_Volunteer", Associatejson) { response, statusCode, error ->
                    if (statusCode != 200) {
                        Log.i("LOG - error", error.toString())

                    } else {
                        Log.i("LOG - login", "SUCCESS")
                    }
                }



                //update isOccupied
                /*
                val json_update_user = JSONObject()
                json_update_user.put("id", StorageManager.instance.get<String>(StorageTypes.USER_ID.toString()))

                NetworkManager.instance.put<Int>("VolunteerUsers/"+ StorageManager.instance.get<String>(
                    StorageTypes.USER_ID.toString()), json_update_user){ response, statusCode, error ->
                    if (statusCode != 204){
                        Log.i("LOG - error in update user", error.toString())
                        loginError?.post {
                            loginError.visibility = View.VISIBLE
                        }
                    }else{
                        Log.i("LOG - volunteer user created ", "")
                        loginError?.post{
                            loginError.text = "Your details updated"
                            loginError.visibility = View.VISIBLE
                        }
                    }
                }

*/

                //val intent = Intent(context, VolunteerUserListAdapter::class.java)

 /*               val intent =Intent(Intent.ACTION_SENDTO);
                intent.setType("text/plain");

                //emailLauncher.type = "message/rfc822"
                try {
                    context.startActivity(intent)
                    //startActivity(emailLauncher)
                }
                catch (e: ActivityNotFoundException) {
                    print(e)
                }
*/
                // Your code that you want to execute on this button click
            }
        })



            return rowView
    }

}

