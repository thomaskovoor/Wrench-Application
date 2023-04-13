package com.example.wrenchapp.datamodel

data class ServerDetailsResponse(
    val ErrorMsg: List<String>,
    val OperationStatus: Int,
    val ServerDetailsList: List<ServerDetails>,
    val Token: String,
    val WarningMsg: Any
)