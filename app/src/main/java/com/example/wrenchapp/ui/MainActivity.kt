package com.example.wrenchapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.wrenchapp.R
import com.example.wrenchapp.datamodel.FolderStructure_LevelInfo

class MainActivity : AppCompatActivity(){


    private lateinit var navController : NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment)
        as NavHostFragment
        navController = navHostFragment.navController


    //http://192.168.1.251/2023.1.2.0_WrenchService/SVC/AtomSVc.svc   -Internal

    // https://qa.wrenchsp.com/2023.1.2.0_wrenchservice/SVC/AtomSVC.svc  -External


    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || super.onSupportNavigateUp()
    }


}