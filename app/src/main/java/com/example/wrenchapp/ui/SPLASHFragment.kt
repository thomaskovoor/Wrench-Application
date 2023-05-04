package com.example.wrenchapp.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.wrenchapp.MyApplication
import com.example.wrenchapp.R
import com.example.wrenchapp.datamodel.Resource
import com.example.wrenchapp.elements.CustomProgressBar
import com.example.wrenchapp.viewmodels.LOGINViewModel
import com.example.wrenchapp.viewmodels.URLViewModel

class SPLASHFragment : Fragment() {
    private lateinit var sf : SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var dialog: CustomProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_s_p_l_a_s_h, container, false)

        sf = MyApplication.appContext.getSharedPreferences("my_sf_folder", AppCompatActivity.MODE_PRIVATE)
        editor=sf.edit()

        val urlViewModel = ViewModelProvider(this)[URLViewModel::class.java]
        val loginViewModel =  ViewModelProvider(this)[LOGINViewModel::class.java]
        dialog = CustomProgressBar(activity)

        val checkBoxValue = sf.getBoolean("isChecked",false)
        val url = sf.getString("Url",null)
        val userName = sf.getString("Username","")
        val password = sf.getString("Password","")


        if(url == null){
            findNavController().navigate(R.id.action_SPLASHFragment_to_URLFragment)
        }
        else{
            if(checkBoxValue){
                urlViewModel.validateUrl(url)
                urlViewModel.urlResponseLiveData.observe(viewLifecycleOwner) { result ->
                    when(result){

                        is Resource.Failure ->{
                            dialog!!.dismissDialog()
                            findNavController().navigate(R.id.action_SPLASHFragment_to_URLFragment)
                        }

                        is Resource.Loading ->{
                          //  dialog!!.showDialog()
                        }

                        is  Resource.Success ->{
                            dialog!!.dismissDialog()
                            loginViewModel.validateControlSetting()
                            loginViewModel.validateGuestLogin(userName!!)
                            loginViewModel.validateLogin(userName,password)

                        }
                    }
                }

                loginViewModel.loginResponseLiveData.observe(viewLifecycleOwner){ result ->
                    when(result){
                        is Resource.Failure -> {
                          //  dialog!!.dismissDialog()
                            findNavController().navigate(R.id.action_SPLASHFragment_to_LOGINFragment)

                        }
                        is Resource.Loading -> {
                       //     dialog!!.showDialog()
                        }
                        is Resource.Success -> {
                          //  dialog!!.dismissDialog()
                            findNavController().navigate(R.id.action_SPLASHFragment_to_HOMEFragment)
                        }

                    }
                }


            }
            else{
                findNavController().navigate(R.id.action_SPLASHFragment_to_URLFragment)
            }

        }



        return view
    }
}