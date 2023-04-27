package com.example.wrenchapp.datamodel

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




 class DataList {

     @SerializedName("LOGIN")
     @Expose
     private val LOGIN: List<List<Common_Format>>? = null
     fun getGuestLoginDetails(): List<List<Common_Format>>? { return LOGIN }


     @SerializedName("USER_DTL")
     @Expose
     private var USER_DTL: List<List<Common_Format>>? = null
     fun getUserDetails(): List<List<Common_Format>>? { return USER_DTL }


     @SerializedName("PERSONAL_FOLDER_LEVEL_INFO")
     @Expose
     private var PERSONAL_FOLDER_LEVEL_INFO:List<List<Common_Format>>? = null
     fun getPersonalFolderStructure() : List<List<Common_Format>>?{ return PERSONAL_FOLDER_LEVEL_INFO }


     @SerializedName("PERSONAL_FOLDER_LEVELS")
     @Expose
     private var PERSONAL_FOLDER_LEVELS:List<List<Common_Format>>? = null
     fun getPersonalFolderDetailsLevels() : List<List<Common_Format>>?{ return PERSONAL_FOLDER_LEVELS }


     @SerializedName("PERSONAL_FOLDER_SYSTEM_RULES_VISIBILITY_INFO")
     @Expose
     private var PERSONAL_FOLDER_SYSTEM_RULES_VISIBILITY_INFO:List<List<Common_Format>>? = null
     fun getPersonalFolderDetailsVisibility() : List<List<Common_Format>>?{ return PERSONAL_FOLDER_SYSTEM_RULES_VISIBILITY_INFO }



 }