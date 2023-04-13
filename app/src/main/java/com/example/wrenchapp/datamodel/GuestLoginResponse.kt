package com.example.wrenchapp.datamodel

data class GuestLoginResponse(
    val DataList: DataListX,
    val ObjectInfo: List<Any>,
    val ProcessDetails: List<ProcessDetailX>,
    val Token: Any
)