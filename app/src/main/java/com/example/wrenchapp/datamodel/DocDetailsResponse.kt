package com.example.wrenchapp.datamodel

data class DocDetailsResponse(
    val ErrorMsg: List<Any>,
    val OperationStatus: Int,
    val Token: String,
    val WarningMsg: Any,
    val groupCountList: Any,
    val smartFolderDocDetails: List<SmartFolderDocDetail>,
    val totalCount: Int
)