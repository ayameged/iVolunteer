package com.ivolunteer.ivolunteer

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment

class RegActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg)
        setSupportActionBar(findViewById(R.id.toolbar))

//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment2) as NavHostFragment
//        val navController = navHostFragment.navController
//
//        navController.navigate(R.id.action_FirstFragment_to_SecondFragment)
    }
}