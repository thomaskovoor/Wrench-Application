package com.example.wrenchapp.datamodel

data class FilterCriteria(
    val CONDITION_END1: String,
    val CONDITION_START1: String,
    val DATA_TYPE: Int,
    val OPERATOR: Int,
    val PROPERTY: String,
    val PROPERTY_VALUE: String,
    val SL_NO: Int
)