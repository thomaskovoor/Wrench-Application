package com.example.wrenchapp.datamodel

data class CountInfoResponse(
    val ActionItemCount: Int,
    val CPWorkSpaceCount: Int,
    val DocumentWorkspaceCount: DocumentWorkspaceCount,
    val ErrorMsg: String,
    val MailBoxCount: Int,
    val OperationStatus: Int,
    val RiskActionItemCount: Int,
    val TaskWorkSpaceCount: Int,
    val Token: String,
    val WarningMsg: Any
)