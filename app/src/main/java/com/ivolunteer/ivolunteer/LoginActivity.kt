package com.ivolunteer.ivolunteer

import android.R.attr.password
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View.OnFocusChangeListener
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.iid.FirebaseInstanceId
import com.ivolunteer.ivolunteer.resources.NetworkManager
import com.ivolunteer.ivolunteer.resources.StorageManager
import com.ivolunteer.ivolunteer.resources.StorageTypes
import com.ivolunteer.ivolunteer.types.Auth
import org.json.JSONObject


enum class UserTypes{
    ApplicationUser,
    VolunteerUser,
    NeedHelpUser
}

class LoginActivity : AppCompatActivity() {
    private val sharedPrefFile = "kotlinsharedpreference" //meital
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE) //meital

        StorageManager.instance.set(StorageTypes.SHARED_PREFERENCE.toString(),sharedPreferences)

        val loginButton = findViewById<Button>(R.id.login_button)
        val userNameInput = findViewById<EditText>(R.id.user_name)
        val passwordInput = findViewById<EditText>(R.id.password)
        val loginError = findViewById<TextView>(R.id.unaothirized_messge)
        val registerButton = findViewById<Button>(R.id.register_new_user)



        val sharedNameValue = sharedPreferences.getString("username","")
        val sharedPasswordValue = sharedPreferences.getString("password","")
        if(sharedNameValue.equals("") && sharedPasswordValue.equals("")){
            userNameInput.setText("${sharedNameValue}").toString()
            passwordInput.setText("${sharedPasswordValue.toString()}")
        }else{
            userNameInput.setText(sharedNameValue).toString()
            passwordInput.setText(sharedPasswordValue.toString())
        }





        userNameInput?.post {
            userNameInput.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
                if (hasFocus) userNameInput.setHint(
                    ""
                ) else userNameInput.setHint("UserName")
            })
        }

        passwordInput?.post {
            passwordInput.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
                if (hasFocus) passwordInput.setHint(
                    ""
                ) else passwordInput.setHint("Password")
            })
        }


        loginButton.setOnClickListener {

           val token= FirebaseInstanceId.getInstance().getToken()

            val json = JSONObject()
            json.put("username", userNameInput.text)
            json.put("password", passwordInput.text)


            val name:String = userNameInput.text.toString()
            val password:String = passwordInput.text.toString()
            val editor:SharedPreferences.Editor =  sharedPreferences.edit()
            editor.putString("username",name)
            editor.putString("password",password)
            editor.apply()
            editor.commit()



            NetworkManager.instance.post<Auth>("authenticate/login", json) { response, statusCode, error ->
                if (statusCode != 200) {
                    Log.i("LOG - error", error.toString())

                    runOnUiThread() {
                        Toast.makeText(
                            applicationContext,
                            "Username or password incorrect",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                else {
                    Log.i("LOG - login", "SUCCESS")
                    StorageManager.instance.set(StorageTypes.TOKEN.toString(), response!!.token)
                    Log.i(
                        "LOG - token: ",
                        StorageManager.instance.get<String>(StorageTypes.TOKEN.toString())!!
                    )
                    NetworkManager.instance.get<String>("applicationUsers/ByUserName?username=" + userNameInput.text){ response, statusCode, error ->
                        if(statusCode == 200){
                            StorageManager.instance.set(StorageTypes.USER_ID.toString(), response!!)
                            NetworkManager.instance.get<String>(
                                "applicationusers/byuserid?id=" + StorageManager.instance.get<String>(
                                    StorageTypes.USER_ID.toString()
                                )
                            ){ response, statusCode, error ->
                                if(statusCode == 200){
                                    StorageManager.instance.set(StorageTypes.USER_TYPE.toString(),response!!
                                    )
                                    Log.i(
                                        "LOG - user type: ",
                                        StorageManager.instance.get<String>(StorageTypes.USER_TYPE.toString())!!
                                    )
                                    if (response == UserTypes.NeedHelpUser.name){
                                        StorageManager.instance.set(StorageTypes.IS_VOLUNTEER.toString(),false)

                                        try {
                                            val activityIntentNeedHelpUser = Intent(
                                                this,
                                                NeedHelpActivity::class.java
                                            )
                                            startActivity(activityIntentNeedHelpUser)
                                        } catch (E: Exception) {
                                            Log.i("error", E.toString())
                                        }
                                    }else if(response == UserTypes.VolunteerUser.name){
                                        StorageManager.instance.set(StorageTypes.IS_VOLUNTEER.toString(),true)
                                        try{
                                            val activityIntentVolunteerUser = Intent(
                                                this,
                                                VolunteerActivity::class.java
                                            )
                                            startActivity(activityIntentVolunteerUser)
                                        }
                                        catch (E: Exception) {
                                            Log.i("error", E.toString())
                                        }
                                    }
                                }
                                else{
                                    Log.i("LOG - failed to get user type", error.toString())
                                }
                            }
                        }
                        else{
                            Log.i("LOG - failed to get user id", error.toString())
                        }
                    }
                }
            }
        }

        registerButton.setOnClickListener {
            val activityIntentRegister = Intent(this, RegActivity::class.java)
            startActivity(activityIntentRegister)
        }
    }
}