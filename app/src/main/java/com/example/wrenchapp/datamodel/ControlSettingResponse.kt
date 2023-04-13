package com.example.wrenchapp.datamodel

data class ControlSettingResponse(
    val AllowAutoCloseCheckpoints: Int,
    val CalendarID: Int,
    val ChecklistMustBeCompleted: Int,
    val DecimalForPercentageValues: Int,
    val EmailLoginEnabled: Int,
    val ErrorMsg: String,
    val IsStrongPasswordEnforced: Int,
    val ManualWFStageApproveAll: Int,
    val OperationStatus: Int,
    val OrganisationCurrencySymbol: String,
    val OrganisationEmail: String,
    val PhoneLoginEnabled: Int,
    val ProjectIDMandatoryForSignInMobile: Int,
    val SystemID: String,
    val Token: Any,
    val WarningMsg: Any
)