package com.example.wrenchapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.wrenchapp.R

class MainActivity : AppCompatActivity() {


    private lateinit var navController : NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment)
        as NavHostFragment
        navController = navHostFragment.navController

    //http://192.168.1.251/2023.1.2.0_WrenchService/SVC/AtomSVc.svc   -Internal

    // https://qa.wrenchsp.com/2023.1.2.0_wrenchservice/SVC/AtomSVC.svc  -External





    }
}