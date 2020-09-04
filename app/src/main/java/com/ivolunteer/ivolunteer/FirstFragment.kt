package com.ivolunteer.ivolunteer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.ivolunteer.ivolunteer.resources.NetworkManager
import com.ivolunteer.ivolunteer.resources.StorageManager
import com.ivolunteer.ivolunteer.resources.StorageTypes
import com.ivolunteer.ivolunteer.types.Auth
import com.ivolunteer.ivolunteer.types.City
import org.json.JSONObject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signUpBtn = view.findViewById<Button>(R.id.sign_up_btn)
        val userName = view.findViewById<EditText>(R.id.user_name_register_text)
        val email = view.findViewById<EditText>(R.id.email_register_text)
        val password = view.findViewById<EditText>(R.id.password_register_text)
        val registerErrorMessage = view.findViewById<TextView>(R.id.register_error_lbl)

        signUpBtn.setOnClickListener {
            val json = JSONObject()
            StorageManager.instance.set(StorageTypes.USER_NAME.toString(), userName.text.toString())
            json.put("username", userName.text)
            json.put("email", email.text)
            json.put("password", password.text)


            NetworkManager.instance.post<Auth>("authenticate/register", json) { response, statusCode, error ->
                if (statusCode != 200) {
                    Log.i("LOG - failed to register", error.toString())
                    registerErrorMessage.post {
                        registerErrorMessage.visibility = View.VISIBLE
                    }
                }
                else{
                    Log.i("LOG - registratuin", "SUCCESS")
                    NetworkManager.instance.post<Auth>("authenticate/login", json) { response, statusCode, error ->
                        if (statusCode != 200) {
                            Log.i("LOG - failed to login", error.toString())
                            registerErrorMessage.post {
                                registerErrorMessage.visibility = View.VISIBLE
                            }
                        }
                        else{
                            Log.i("LOG - login", "SUCCESS")
                            StorageManager.instance.set(StorageTypes.TOKEN.toString(), response!!.token)
                            NetworkManager.instance.get<String>("applicationUsers/ByUserName?username="+userName.text.toString()){response, statusCode, error ->
                                if(statusCode == 200){
                                    StorageManager.instance.set(StorageTypes.USER_ID.toString(), response!!)
                                }
                                else{
                                    Log.i("LOG - failed to get user id", error.toString())
                                }
                            }
                            val navController = Navigation.findNavController((activity as RegActivity), R.id.fragment)
                            navController.navigate(R.id.action_FirstFragment_to_SecondFragment)
                        }
                    }
                }
            }

//            var userName = StorageManager.instance.get<String>(StorageTypes.USER_NAME.toString())


        }
    }
}