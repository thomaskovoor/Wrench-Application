package com.example.wrenchapp.datamodel

data class LoginRequest(
    val GPS_ALTITUDE: Double,
    val GPS_LATITUDE: Double,
    val GPS_LOCATION: String,
    val GPS_LONGITUDE: Double,
    val IS_PASSWORD_ENCRYPTED: Int,
    val LOGIN_NAME: String,
    val PASSWORD: String,
    val SERVER_ID: Int,
    val WORKSTATION_ID: String
)