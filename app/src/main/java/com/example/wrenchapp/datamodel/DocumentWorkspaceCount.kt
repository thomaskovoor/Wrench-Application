package com.example.wrenchapp.datamodel

data class DocumentWorkspaceCount(
    val DueTodayDocument: Int,
    val ForActionCount: Int,
    val ForInformationCount: Int,
    val ForwardedCount: Int,
    val NewDocumentCount: Int,
    val OverDueDocument: Int,
    val TotalDocument: Int
)