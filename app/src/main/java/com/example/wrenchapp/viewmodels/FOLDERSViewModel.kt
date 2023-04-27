package com.example.wrenchapp.viewmodels

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import retrofit2.create
import kotlin.math.log

class FOLDERSViewModel : ViewModel() {

    private lateinit var sf : SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    var _FolderStructResponseLiveData = MutableLiveData<Resource<List<FolderStructure_LevelInfo>?>>()
    val FolderStructResponseLiveData: MutableLiveData<Resource<List<FolderStructure_LevelInfo>?>> get() = _FolderStructResponseLiveData

    var _FolderDetailsResponseLiveData = MutableLiveData<Resource<List<FolderStructure_LevelInfo>?>>()
    val FolderDetailsResponseLiveData:MutableLiveData<Resource<List<FolderStructure_LevelInfo>?>> get() = _FolderDetailsResponseLiveData


 /*   fun getCountInfo(){

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
                        TODO("Not yet implemented")
                    }
                    override fun onFailure(call: Call<CountInfoResponse>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                }
            )
        }

    }*/


    fun getFolderStructure(){



        _FolderStructResponseLiveData.postValue(Resource.Loading)
        sf = MyApplication.appContext.getSharedPreferences("my_sf_folder", AppCompatActivity.MODE_PRIVATE)
        editor=sf.edit()
        val loginToken = sf.getString("Login_Token","")
        val objectProperty = listOf(ObjectPropertyX("OBJECT_TYPE",0),
            ObjectPropertyX("FILTER_CONDITION","IS_HIDDEN = 0 AND PARENT_IS_HIDDEN = 0 AND (LEVEL_NUMBER = 1 OR ORPHAN = 1) AND ((OBJECT_TYPE=0 AND OBJECT_SUB_TYPE<=0) OR OBJECT_TYPE=-1) AND (SYS_CRITERIA_ID IS NULL OR SYS_CRITERIA_ID=6) "))
       viewModelScope.launch {
           val request = PersonalFolderRequest(loginToken!!,objectProperty)
           val apiInterface = getNucleusRetrofitInstance()?.create(NucleusApiInterface::class.java)
           apiInterface?.getPersonalFolderStruct(request)?.enqueue(
               object : Callback<DataResponse>{
                   override fun onResponse(
                       call: Call<DataResponse>,
                       response: Response<DataResponse>
                   ) {
                       if(response.isSuccessful){
                           val folderStructure : List<FolderStructure_LevelInfo>
                           val data:DataResponse = response.body()!!
                           val commonHelper = CommonService()
                           folderStructure = if(data.getDataList()!!.getPersonalFolderStructure()!=null){
                               if(data.getDataList()!!.getPersonalFolderStructure()!!.isNotEmpty()){
                                   val structure = FolderStructure_LevelInfo()
                                   commonHelper.parseNucleusData(data.getDataList()!!.getPersonalFolderStructure(),structure)
                                           as List<FolderStructure_LevelInfo>
                               } else{
                                   ArrayList()
                               }
                           } else{
                               ArrayList()
                           }
                           if (data.getProcessDetails()!![0].ErrorDetails.isEmpty() && data.getToken()!= null){
                               _FolderStructResponseLiveData.postValue(Resource.Success(folderStructure))
                           }
                           else{
                               _FolderStructResponseLiveData.postValue(Resource.Failure(false,0,data.getProcessDetails()!![0].ErrorDetails))
                           }

                       }
                       else{
                           _FolderStructResponseLiveData.postValue(Resource.Failure(false,0, listOf("Empty Response")))
                       }
                   }

                   override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                       _FolderStructResponseLiveData.postValue(Resource.Failure(false,0, listOf("Network Error")))
                   }

               }
           )
       }

    }

    fun getPersonalFolderDetails(id:Int){
_FolderDetailsResponseLiveData.postValue(Resource.Loading)
        val loginToken = sf.getString("Login_Token","")

        val objectProperties = listOf(ObjectPropertyX("FOLDER_ID",id),
            ObjectPropertyX("PERSONAL_FOLDER_PROPERTIES", listOf(10)),
            ObjectPropertyX("FETCH_MODE",1),
            ObjectPropertyX("FILTER_CONDITION","IS_HIDDEN = 0 AND PARENT_IS_HIDDEN = 0 AND ((OBJECT_TYPE=0 AND OBJECT_SUB_TYPE<=0) OR OBJECT_TYPE=-1) AND (SYS_CRITERIA_ID IS NULL OR SYS_CRITERIA_ID=6)")
        )

        viewModelScope.launch {
            val request = PersonalFolderDetailsRequest(loginToken!!,objectProperties)
            val apiInterface = getNucleusRetrofitInstance()?.create(NucleusApiInterface::class.java)
            apiInterface?.getPersonalFolderDetails(request)?.enqueue(
                object : Callback<DataResponse> {
                    override fun onResponse(
                        call: Call<DataResponse>,
                        response: Response<DataResponse>
                    ) {
                        if (response.isSuccessful) {
                            val folderDetails: List<FolderStructure_LevelInfo>
                            val data:DataResponse = response.body()!!
                            val commonHelper = CommonService()
                            folderDetails = if(data.getDataList()!!.getPersonalFolderDetailsLevels()!=null){
                                if(data.getDataList()!!.getPersonalFolderDetailsLevels()!!.isNotEmpty()){
                                    val details = FolderStructure_LevelInfo()
                                    commonHelper.parseNucleusData(data.getDataList()!!.getPersonalFolderDetailsLevels(),details) as List<FolderStructure_LevelInfo>
                                }
                                else {
                                    ArrayList()
                                }
                            }
                            else{
                                ArrayList()
                            }
                            if (data.getProcessDetails()!![0].ErrorDetails.isEmpty() && data.getToken()!= null){
                                _FolderDetailsResponseLiveData.postValue(Resource.Success(folderDetails))
                            }
                            else{
                                _FolderDetailsResponseLiveData.postValue(Resource.Failure(false,0,data.getProcessDetails()!![0].ErrorDetails))
                            }
                        }
                        else{
                            _FolderDetailsResponseLiveData.postValue(Resource.Failure(false,0, listOf("Empty Response")))
                        }
                    }


                    override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                        _FolderDetailsResponseLiveData.postValue(Resource.Failure(false,0, listOf("Network Error")))
                    }

                }
            )
        }
    }

}