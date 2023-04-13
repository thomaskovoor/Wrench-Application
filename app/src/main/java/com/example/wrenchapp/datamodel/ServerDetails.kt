package com.example.wrenchapp.datamodel

data class ServerDetails(
    val AtomServiceExternalURL: String,
    val AtomServiceURL: String,
    val NucleusWebAPIUrl: String,
    val ServerIdentifier: String,
    val ServerUrl: String,
    val WebAPIExternalUrl: String,
    val internalServerUrl: String
)