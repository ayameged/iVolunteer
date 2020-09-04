package com.ivolunteer.ivolunteer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.ivolunteer.ivolunteer.resources.NetworkManager
import com.ivolunteer.ivolunteer.resources.StorageManager
import com.ivolunteer.ivolunteer.resources.StorageTypes
import com.ivolunteer.ivolunteer.types.Auth
import org.json.JSONObject
import android.util.Log



class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.login_button)
        val userNameInput = findViewById<EditText>(R.id.user_name)
        val passwordInput = findViewById<EditText>(R.id.password)
        val loginError = findViewById<TextView>(R.id.unaothirized_messge)
        val registerButton = findViewById<Button>(R.id.register_new_user)

        loginButton.setOnClickListener {

            val json = JSONObject()
            json.put("username", userNameInput.text)
            json.put("password", passwordInput.text)

            NetworkManager.instance.post<Auth>("authenticate/login", json) { response, statusCode, error ->
                if (statusCode != 200) {
                    Log.i("LOG - error", error.toString())

                    loginError.post {
                        loginError.visibility = View.VISIBLE
                    }
                }
                else {
                    StorageManager.instance.set(StorageTypes.TOKEN.toString(), response!!.token)
                    Log.i("LOG - token: ", StorageManager.instance.get<String>(StorageTypes.TOKEN.toString())!!)
                }
            }
        }

        registerButton.setOnClickListener {
            val activityIntentRegister = Intent(this, RegActivity::class.java)
            startActivity(activityIntentRegister)
        }
    }
}