package com.example.wrenchapp.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.wrenchapp.R
import com.example.wrenchapp.viewmodels.DOCUMENTSViewModel

class DOCUMENTSFragment : Fragment() {

    private lateinit var sf : SharedPreferences
    private lateinit var editor: SharedPreferences.Editor


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_d_o_c_u_m_e_n_t_s, container, false)

        val viewModel = ViewModelProvider(this)[DOCUMENTSViewModel::class.java]
        return view
    }


            }

