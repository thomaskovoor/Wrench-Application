package com.example.wrenchapp.datamodel

data class RuleCriteriaRequest(
    val LoginName: String,
    val ServerId: Int,
    val SmartFolderID: Int,
    val Token: String
)