package com.example.wrenchapp.network


import com.example.wrenchapp.datamodel.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface AtomApiInterface{

    @GET("Utilities/IsIntendedURL")
    fun getIntendedURL(): Call<String>

    @POST("Access/GetServerDetailsBasedOnSVCURL")
    fun getServerDetails(@Body serverRequest:ServerDetailsRequest):Call<ServerDetailsResponse>

    @POST("Utilities/GetControlSettings")
    fun getControlSettings(@Body controlSettingRequest : ControlSettingRequest):Call<ControlSettingResponse>

    @POST("Access/Logout")
    fun userLogout(@Body logoutRequest : LogoutRequest):Call<LogoutResponse>

    @POST("Utilities/GetCountInfoObjectTypeWise")
    fun getCountInfo(@Body countInfoRequest : CountInfoRequest ):Call<CountInfoResponse>

    @POST("Utilities/GetSmartFolderRuleCriteriaMappingValues")
    fun getRuleCriteria(@Body ruleCriteriaRequest: RuleCriteriaRequest):Call<RuleCriteriaResponse>

    @POST("SmartFolder/GetSmartFolderDocDetails")
    fun getSmartFolderDocDetails(@Body docDetailsRequest : DocDetailsRequest):Call<DocDetailsResponse>



    }
