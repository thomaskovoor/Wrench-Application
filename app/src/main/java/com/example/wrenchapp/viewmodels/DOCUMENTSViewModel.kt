package com.example.wrenchapp.viewmodels

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wrenchapp.MyApplication
import com.example.wrenchapp.datamodel.*
import com.example.wrenchapp.network.AtomApiInterface
import com.example.wrenchapp.network.RetrofitInstance.Companion.getAtomRetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DOCUMENTSViewModel : ViewModel() {

    private lateinit var sf : SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    var _DocDetailsResponseLiveData = MutableLiveData<Resource<DocDetailsResponse>>()
    val DocDetailsResponseLiveData : MutableLiveData<Resource<DocDetailsResponse>> get() = _DocDetailsResponseLiveData
    var i = 1

    fun getSmartFolderRuleCriteria(docID: Int?) {
        _DocDetailsResponseLiveData.postValue(Resource.Loading)
        sf = MyApplication.appContext.getSharedPreferences("my_sf_folder", AppCompatActivity.MODE_PRIVATE)
        editor = sf.edit()

        val serverId = 1
        val loginName = sf.getString("Username", "")
        val loginToken = sf.getString("Login_Token", "")

        viewModelScope.launch {
            val request = RuleCriteriaRequest(loginName!!, serverId, docID!!, loginToken!!)

            val apiInterface = getAtomRetrofitInstance()?.create(AtomApiInterface::class.java)
            apiInterface?.getRuleCriteria(request)?.enqueue(
                object : Callback<RuleCriteriaResponse> {
                    override fun onResponse(
                        call: Call<RuleCriteriaResponse>,
                        response: Response<RuleCriteriaResponse>
                    ) {
                        if(response.isSuccessful){
                            val criteriaResponse : RuleCriteriaResponse = response.body()!!
                            if(criteriaResponse.ErrorMsg == null && criteriaResponse.Token.isNotEmpty() ){
                                getSmartFolderDocDetails(docID,1)
                            }
                            else{
                               _DocDetailsResponseLiveData.postValue(Resource.Failure(false,0, listOf("Response Error")))
                            }
                        }
                        else{
                            _DocDetailsResponseLiveData.postValue(Resource.Failure(false,1, listOf("Response Error")))
                        }
                    }

                    override fun onFailure(call: Call<RuleCriteriaResponse>, t: Throwable) {
                        _DocDetailsResponseLiveData.postValue(Resource.Failure(false,1, listOf("Network Error")))
                    }

                }
            )
        }
        }
    fun getSmartFolderDocDetails(id:Int,pageNum : Int){

        sf = MyApplication.appContext.getSharedPreferences("my_sf_folder", AppCompatActivity.MODE_PRIVATE)
        editor=sf.edit()

        val serverId = 1
        val loginName = sf.getString("Username","")
        val loginToken = sf.getString("Login_Token","")

        val filterCriteria = "(ISNULL(DOC_CORRESPONDENCE.CORR_SOURCE,0)<>1)";val folderCriteria =  -1; val folderId = id.toString()
         val folderSubType = 0;val folderType = 0;val getCount = false;
        val pageSize = 20
         val visibleColumns = "ITEM_ID,INT_REV_NO,VERSION_NO,ITEM_DESCR,ITEM_NO,REV_NO_VALUE,ORDER_NO,CREATED_ON,USER_REV_NO_VALUE,GENO_KEY,ORDER_ID,DOC_TYPE,FILE_TYPE"

        viewModelScope.launch {
          val request = DocDetailsRequest(filterCriteria,folderCriteria,folderId,
              folderSubType,folderType,getCount,loginName!!,pageNum,pageSize,serverId,loginToken!!,visibleColumns)

            val apiInterface = getAtomRetrofitInstance()?.create(AtomApiInterface::class.java)
            apiInterface?.getSmartFolderDocDetails(request)?.enqueue(
                object : Callback<DocDetailsResponse>{
                    override fun onResponse(
                        call: Call<DocDetailsResponse>,
                        response: Response<DocDetailsResponse>
                    ) {
                        if(response.isSuccessful){
                            val docDetails : DocDetailsResponse = response.body()!!
                            if(docDetails.ErrorMsg.isEmpty() && docDetails.Token.isNotEmpty()){
                                _DocDetailsResponseLiveData.postValue(Resource.Success(docDetails))
                            }
                            else{
                                _DocDetailsResponseLiveData.postValue(Resource.Failure(false,0,
                                    docDetails.ErrorMsg))
                            }
                        }
                        else{
                            _DocDetailsResponseLiveData.postValue(Resource.Failure(false,0,
                                listOf("Empty Response")))
                        }
                    }

                    override fun onFailure(call: Call<DocDetailsResponse>, t: Throwable) {
                        _DocDetailsResponseLiveData.postValue(Resource.Failure(false,0,
                            listOf("Network Error")))
                    }
                }
            )

        }
    }


}

