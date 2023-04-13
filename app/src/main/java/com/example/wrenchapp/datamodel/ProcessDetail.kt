package com.example.wrenchapp.datamodel

data class ProcessDetail(
    val ErrorDetails: List<String>,
    val Errors: Any,
    val ObjectDesc: String,
    val ObjectId: Int,
    val ObjectType: Int,
    val ProcessDesc: String,
    val ProcessId: Int,
    val ProcessStatus: Int,
    val SubObjects: List<Any>
)