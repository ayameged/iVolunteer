package com.ivolunteer.ivolunteer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.ivolunteer.ivolunteer.resources.NetworkManager
import com.ivolunteer.ivolunteer.types.Auth
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
        var registered = false

        signUpBtn.setOnClickListener {
            val json = JSONObject()
            json.put("username", userName.text)
            json.put("email", email.text)
            json.put("password", password.text)

            val navController = Navigation.findNavController((activity as RegActivity), R.id.fragment)
            navController.navigate(R.id.action_FirstFragment_to_SecondFragment)

            NetworkManager.instance.post<Auth>("authenticate/register", json) { response, statusCode, error ->
                print(response)
                if (statusCode != 200) {
                    print(error)
                }
                else{
                    registered = true
                }
            }
            if (registered){
                NetworkManager.instance.post<Auth>("authenticate/login", json) { response, statusCode, error ->
                    print(response)
                    if (statusCode != 200) {
                        print(error)
                    }
                }
            }
        }
    }
}