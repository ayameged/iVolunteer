package com.ivolunteer.ivolunteer

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.ivolunteer.ivolunteer.resources.StorageManager
import com.ivolunteer.ivolunteer.resources.StorageTypes
import kotlinx.android.synthetic.main.fragment_second.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        i_need_help_btn.setOnClickListener {
            moveToFillDetailsPage()
            StorageManager.instance.set(StorageTypes.IS_VOLUNTEER.toString(), false)
            StorageManager.instance.set(StorageTypes.USER_TYPE.toString(), "NeedHelpUser")
        }

        i_volunteer_btn.setOnClickListener {
            moveToFillDetailsPage()
            StorageManager.instance.set(StorageTypes.IS_VOLUNTEER.toString(), true)
            StorageManager.instance.set(StorageTypes.USER_TYPE.toString(), "VolunteerUser")
        }
    }

    private fun moveToFillDetailsPage(){
        val navController = Navigation.findNavController((activity as RegActivity), R.id.fragment)
        navController.navigate(R.id.action_SecondFragment_to_ThirdFragment)
    }
}