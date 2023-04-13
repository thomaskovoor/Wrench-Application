package com.example.wrenchapp.ui

import android.app.Activity
import android.app.Dialog
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.wrenchapp.MyApplication
import com.example.wrenchapp.R
import com.example.wrenchapp.viewmodels.HOMEViewModel


class HOMEFragment : Fragment() {
 private  var thisTime:Long = 0
    private var i : Int = 0

    private lateinit var sf : SharedPreferences
    private lateinit var editor : SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        sf = MyApplication.appContext.getSharedPreferences("my_sf_folder", AppCompatActivity.MODE_PRIVATE)
        editor=sf.edit()
        val dialogBinding = layoutInflater.inflate(R.layout.custom_alert_dialog,null)
        val myDialog = Dialog(requireContext())
        myDialog.setContentView(dialogBinding)
        myDialog.setCancelable(false)
        myDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val yes_btn = dialogBinding.findViewById<TextView>(R.id.alert_yes)
        val no_btn = dialogBinding.findViewById<TextView>(R.id.alert_no)


        val view = inflater.inflate(R.layout.fragment_h_o_m_e, container, false)
        val viewModel = ViewModelProvider(this)[HOMEViewModel::class.java]

        val logout_btn = view.findViewById<ImageView>(R.id.logout_btn)

        logout_btn.setOnClickListener{
            myDialog.show()
      /*      i++
            val handler = android.os.Handler()

            handler.postDelayed({
                if(i == 1){
                    Toast.makeText(activity,"Press again to logout",Toast.LENGTH_SHORT).show()
                }
                if(i == 2){
                    viewModel.validateLogout()
                     if(viewModel.logoutStatus){
                         sf.edit().remove("Username").apply()
                         sf.edit().remove("Password").apply()
                         sf.edit().remove("isChecked").apply()
                         Toast.makeText(activity,"Logout Successful",Toast.LENGTH_LONG).show()
                      //   findNavController().navigate(R.id.action_HOMEFragment_to_LOGINFragment,null,
                         //    NavOptions.Builder().setPopUpTo(findNavController().graph.getStartDestination(), true).build())

                     }
                }
                i=0
            },500)*/
            yes_btn.setOnClickListener {
                myDialog.dismiss()
                findNavController().navigate(R.id.action_HOMEFragment_to_LOGINFragment)
            }
            no_btn.setOnClickListener {
                myDialog.dismiss()
            }
        }

        return view
    }



    }





