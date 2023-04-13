package com.example.wrenchapp.elements

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.FragmentActivity

class CustomProgressBar(var activity: FragmentActivity?) {
    var dialog:Dialog? = null

    fun  showDialog(){
        dialog = Dialog(activity!!)
        dialog!!.setContentView(com.example.wrenchapp.R.layout.dialog_loading)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(0))
        dialog!!.show()
        dialog!!.setCancelable(false)
    }

    fun dismissDialog(){
       if(dialog == null){
           dialog = Dialog(activity!!)
       }
        dialog!!.dismiss()
        dialog!!.setCanceledOnTouchOutside(true)
    }
}