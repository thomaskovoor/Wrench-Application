package com.example.wrenchapp.viewmodels

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wrenchapp.MyApplication
import com.example.wrenchapp.datamodel.*
import com.example.wrenchapp.network.AtomApiInterface
import com.example.wrenchapp.network.NucleusApiInterface
import com.example.wrenchapp.network.RetrofitInstance.Companion.getAtomRetrofitInstance
import com.example.wrenchapp.network.RetrofitInstance.Companion.getNucleusRetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FOLDERSViewModel : ViewModel() {

    private lateinit var sf : SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    fun getCountInfo(){

        sf = MyApplication.appContext.getSharedPreferences("my_sf_folder", AppCompatActivity.MODE_PRIVATE)
        editor=sf.edit()

        val loginName = sf.getString("Username","")
        val loginToken = sf.getString("Login_Token","")
        val filterCondition = "ISNULL(DC.CORR_SOURCE,0)<>1";val objectType = 6; val serverId =1

        viewModelScope.launch {
            val request = CountInfoRequest(filterCondition,loginName!!,objectType,serverId,loginToken!!)
            val apiInterface = getAtomRetrofitInstance()?.create(AtomApiInterface::class.java)
            apiInterface?.getCountInfo(request)?.enqueue(
                object : Callback<CountInfoResponse>{
                    override fun onResponse(
                        call: Call<CountInfoResponse>,
                        response: Response<CountInfoResponse>
                    ) {
                        if(response.isSuccessful) {

                        }
                    }
                    override fun onFailure(call: Call<CountInfoResponse>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                }
            )
        }

    }


    fun getFolderStructure(){

        sf = MyApplication.appContext.getSharedPreferences("my_sf_folder", AppCompatActivity.MODE_PRIVATE)
        editor=sf.edit()

        val loginToken = sf.getString("Login_Token","")
        val objectProperty = listOf(ObjectPropertyX("OBJECT_TYPE",0),
            ObjectPropertyX("FILTER_CONDITION","IS_HIDDEN = 0 AND PARENT_IS_HIDDEN = 0 AND (LEVEL_NUMBER = 1 OR ORPHAN = 1) AND ((OBJECT_TYPE=0 AND OBJECT_SUB_TYPE<=0) OR OBJECT_TYPE=-1) AND (SYS_CRITERIA_ID IS NULL OR SYS_CRITERIA_ID=6) "))
       viewModelScope.launch {
           val req = PersonalFolderRequest(loginToken!!,objectProperty)
           val apiInterface = getNucleusRetrofitInstance()?.create(NucleusApiInterface::class.java)
           apiInterface?.getPersonalFolderStruct(req)?.enqueue(
               object : Callback<DataResponse>{
                   override fun onResponse(
                       call: Call<DataResponse>,
                       response: Response<DataResponse>
                   ) {
                       TODO("Not yet implemented")
                   }

                   override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                       TODO("Not yet implemented")
                   }

               }
           )
       }

    }

}