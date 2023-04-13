package com.example.wrenchapp.network

import com.example.wrenchapp.datamodel.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface NucleusApiInterface {

    @POST("AccessControl/Login")
    fun userLogin(@Body loginRequest: LoginRequest): Call<DataResponse>

    @POST("AccessControl/GuestLogin")
    fun getGuestLogin(@Body guestLoginRequest: GuestLoginRequest):Call<DataResponse>

    @POST("AccessControl/GetLoginPrerequisites")
    fun getLoginPrerequisites(@Body loginPrerequisites : LoginPrerequisitesRequest):Call<DataResponse>

}

