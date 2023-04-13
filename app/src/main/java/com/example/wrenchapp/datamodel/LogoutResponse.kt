package com.example.wrenchapp.datamodel

data class LogoutResponse(
    val ErrorMsg: String,
    val OperationStatus: Int,
    val Token: String,
    val WarningMsg: String
)