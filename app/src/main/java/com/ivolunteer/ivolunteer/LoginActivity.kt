package com.ivolunteer.ivolunteer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.ivolunteer.ivolunteer.resources.NetworkManager
import com.ivolunteer.ivolunteer.types.Auth
import org.json.JSONObject

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
                print(response)
                if (statusCode != 200) {
                    print(error)

                    loginError.post {
                        loginError.visibility = View.VISIBLE
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