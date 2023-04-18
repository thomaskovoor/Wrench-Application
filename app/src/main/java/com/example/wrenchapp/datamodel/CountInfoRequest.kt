package com.example.wrenchapp.datamodel

data class CountInfoRequest(
    val FilterCondition: String,
    val LoginName: String,
    val ObjectType: Int,
    val ServerId: Int,
    val Token: String
)