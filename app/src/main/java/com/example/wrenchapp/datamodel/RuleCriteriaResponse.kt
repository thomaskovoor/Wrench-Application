package com.example.wrenchapp.datamodel

data class RuleCriteriaResponse(
    val ErrorMsg: Any,
    val GenealogyKey: Int,
    val ObjectType: Int,
    val OperationStatus: Int,
    val ProjectId: Int,
    val SmartFolderRuleCriteria: List<SmartFolderRuleCriteria>,
    val Token: String,
    val WarningMsg: Any
)