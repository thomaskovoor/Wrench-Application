package com.example.wrenchapp.datamodel

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




 class DataList {

     @SerializedName("LOGIN")
     @Expose
     private val LOGIN: List<List<Common_Format>>? = null

     fun getGuestLoginDetails(): List<List<Common_Format>>? {
         return LOGIN
     }




     @SerializedName("USER_DTL")
     @Expose
     private var USER_DTL: List<List<Common_Format>>? = null

     fun getUserDetails(): List<List<Common_Format>>? {
         return USER_DTL
     }

     fun setDataList(dataList: List<List<Common_Format>>?) {
         this.USER_DTL = dataList
     }


 }