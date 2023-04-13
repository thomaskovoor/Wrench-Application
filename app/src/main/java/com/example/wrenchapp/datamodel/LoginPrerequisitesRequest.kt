package com.example.wrenchapp.datamodel

data class LoginPrerequisitesRequest(
    val Token: String,
    val objectProperties: List<ObjectProperty>
)