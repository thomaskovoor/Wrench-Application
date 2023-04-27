package com.example.wrenchapp.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.wrenchapp.MyApplication
import com.example.wrenchapp.R
import com.example.wrenchapp.datamodel.Resource
import com.example.wrenchapp.viewmodels.URLViewModel
import com.example.wrenchapp.elements.CustomProgressBar

class URLFragment : Fragment() {
    @SuppressLint("CutPasteId")

    private lateinit var sf : SharedPreferences
    private lateinit var editor : SharedPreferences.Editor
    private var dialog: CustomProgressBar? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_u_r_l, container, false)

        val dialogBinding = layoutInflater.inflate(R.layout.error_dialog,null)
        val myDialog = Dialog(requireContext())
        myDialog.setContentView(dialogBinding)
        myDialog.setCancelable(false)
        myDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val okBtn = dialogBinding.findViewById<Button>(R.id.closeBtn)
        val alertMsg = dialogBinding.findViewById<TextView>(R.id.showMsg)

        val urlButton1: Button = view.findViewById(R.id.url_btn)
        val urlText1 :EditText = view.findViewById(R.id.url_iptext)
        val viewModel = ViewModelProvider(this)[URLViewModel::class.java]
        dialog = CustomProgressBar(activity)
        urlButton1.setOnClickListener {
            if(urlText1.text.toString().isEmpty()){
                Toast.makeText(activity,"Please Enter URL",Toast.LENGTH_LONG).show()
            }
            else{
                viewModel.validateUrl(urlText1.text.toString())
            }
        }

        okBtn.setOnClickListener {
            myDialog.dismiss()
        }


        viewModel.urlResponseLiveData.observe(viewLifecycleOwner) { result ->
            when(result){

                is Resource.Failure ->{
                    dialog!!.dismissDialog()
                    alertMsg.text = result.errorBody.toString()
                    myDialog.show()
                }

                is Resource.Loading ->{
                    dialog!!.showDialog()
                }

                is  Resource.Success ->{
                    dialog!!.dismissDialog()
                    findNavController().navigate(R.id.action_URLFragment_to_LOGINFragment)
                }
                else -> {}
            }
        }

        return view
    }

    override fun onPause() {
        super.onPause()
        sf = MyApplication.appContext.getSharedPreferences("my_sf_folder", AppCompatActivity.MODE_PRIVATE)
        editor=sf.edit()
        val view : View? = view
        val urlText = view?.findViewById<EditText>(R.id.url_iptext)?.text.toString()

        editor.apply {
            putString("Url", urlText)
            commit()
        }
    }

    override fun onResume() {
        super.onResume()

        sf = MyApplication.appContext.getSharedPreferences("my_sf_folder", AppCompatActivity.MODE_PRIVATE)
        editor=sf.edit()
        val text = sf.getString("Url",null)
        val view : View? = view
        view?.findViewById<EditText>(R.id.url_iptext)?.setText(text)
    }
}