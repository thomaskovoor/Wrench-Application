package com.example.wrenchapp.datamodel

data class DocDetailsRequest(
    val FilterCriteria: String,
    val FolderCriteria: Int,
    val FolderId: String,
    val FolderSubType: Int,
    val FolderType: Int,
    val GetCount: Boolean,
    val LoginName: String,
    val PageNum: Int,
    val PageSize: Int,
    val ServerId: Int,
    val Token: String,
    val VisibleColumns: String
)