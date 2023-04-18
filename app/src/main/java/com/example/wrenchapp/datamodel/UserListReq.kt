package com.example.wrenchapp.datamodel

data class UserListReq(
    val Token: String,
    val filterCriteria: List<FilterCriteria>,
    val objectProperties: List<ObjectPropertyX>
)