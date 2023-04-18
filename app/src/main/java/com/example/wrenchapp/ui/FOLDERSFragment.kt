package com.example.wrenchapp.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.wrenchapp.R
import com.example.wrenchapp.viewmodels.FOLDERSViewModel


class FOLDERSFragment : Fragment() {

    private lateinit var sf : SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_f_o_l_d_e_r_s, container, false)

        val viewModel = ViewModelProvider(this)[FOLDERSViewModel::class.java]






        return view
    }

}