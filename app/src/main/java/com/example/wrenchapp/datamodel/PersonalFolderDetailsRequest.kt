package com.example.wrenchapp.datamodel

data class PersonalFolderDetailsRequest(
    val Token: String,
    val objectProperties: List<ObjectPropertyX>
)