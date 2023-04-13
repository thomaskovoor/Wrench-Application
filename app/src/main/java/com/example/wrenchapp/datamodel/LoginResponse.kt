package com.example.wrenchapp.datamodel

data class LoginResponse(
    val DataList: DataList,
    val ObjectInfo: List<Any>,
    val ProcessDetails: List<ProcessDetail>,
    val Token: Any
)