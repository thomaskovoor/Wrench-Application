@file:Suppress("SpellCheckingInspection", "SpellCheckingInspection", "SpellCheckingInspection",
    "SpellCheckingInspection", "SENSELESS_COMPARISON", "ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE"
)

//removed paranthese for editor.apply

package com.example.wrenchapp.viewmodels
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wrenchapp.MyApplication
import com.example.wrenchapp.datamodel.Resource
import com.example.wrenchapp.datamodel.ServerDetailsRequest
import com.example.wrenchapp.datamodel.ServerDetailsResponse
import com.example.wrenchapp.network.AtomApiInterface
import com.example.wrenchapp.network.RetrofitInstance.Companion.getAtomRetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

@Suppress("PropertyName", "SpellCheckingInspection")
class URLViewModel : ViewModel() {

    var _urlResponseLiveData = MutableLiveData<Resource<ServerDetailsResponse>>()
    val urlResponseLiveData: LiveData<Resource<ServerDetailsResponse>> get() = _urlResponseLiveData
    private lateinit var sf : SharedPreferences
    private lateinit var editor : SharedPreferences.Editor

    val testMsg1 = listOf("Network Error")
    val testMsg2 = listOf("Input Details Error")
    val testMsg3 = listOf("Empty Response")

    fun validateUrl(urlText: String) {

              _urlResponseLiveData.postValue(Resource.Loading)

        sf = MyApplication.appContext.getSharedPreferences("my_sf_folder", AppCompatActivity.MODE_PRIVATE)
        editor=sf.edit()


        val modifiedurl = "$urlText/"
        var urlnew: String = modifiedurl

        if (urlnew.lowercase(Locale.getDefault()).startsWith("https")) {
            urlnew += "https/"
        }
         viewModelScope.launch {

                val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(urlnew)
                    .addConverterFactory(GsonConverterFactory.create())
                    //.client(client)
                    .build()
                val apiInterface = retrofit.create(AtomApiInterface::class.java)
                val call = apiInterface.getIntendedURL()
                call.enqueue(object : Callback<String?> {
                    override fun onResponse(call: Call<String?>, response: Response<String?>) {

                        if (response.isSuccessful) {
                            if (response.body()!!.isNotEmpty()) {
                                if (response.body() == "WrenchMobil") {
                                    Log.d("testing","pass")
                                    editor.apply {
                                        putString("Atom_Base_Url", urlnew)
                                        putString("Server_Url_Input", modifiedurl)
                                        commit()
                                    }
                                    getServerDetails(modifiedurl)
                                } else {
                                    _urlResponseLiveData.postValue(Resource.Failure(false, 0, testMsg2))
                                    Toast.makeText(MyApplication.appContext, "Invalid URL", Toast.LENGTH_SHORT).show()
                                }

                            }
                            else{
                                _urlResponseLiveData.postValue(Resource.Failure(false, 0, testMsg3))
                                Toast.makeText(MyApplication.appContext, "Empty Response", Toast.LENGTH_SHORT).show()
                            }
                        }
                        else{
                            _urlResponseLiveData.postValue(Resource.Failure(false, 0, testMsg3))
                            Toast.makeText(MyApplication.appContext, "Invalid URL", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<String?>, t: Throwable) {
                        _urlResponseLiveData.postValue(Resource.Failure(false,0,testMsg1))

                    }
                })
            }
        }

    fun getServerDetails(modifiedUrl: String)
    {
        var networkType : String
        //_urlResponseLiveData.postValue(Resource.Loading)
        val secretKey = "berDm70gXAkpa32r/g4o0raCYg4SgrCncsoEvz+fWhJd/29pSFvP4CL+yQDFDCn/"
        val serverReq  = ServerDetailsRequest(modifiedUrl,secretKey)
        viewModelScope.launch {

                val apiInterface = getAtomRetrofitInstance()?.create(AtomApiInterface::class.java)
                apiInterface?.getServerDetails(serverReq)?.enqueue(
                    object : Callback<ServerDetailsResponse> {
                        override fun onResponse(
                            call: Call<ServerDetailsResponse>,
                            response: Response<ServerDetailsResponse>
                        ) {

                            if (response.isSuccessful) {
                                    val serverResponse: ServerDetailsResponse = response.body()!!
                                    if (serverResponse.ServerDetailsList.isNotEmpty()) {
                                        if (serverResponse.ErrorMsg == null) {

                                            if (serverResponse.ServerDetailsList[0].AtomServiceURL.equals(sf.getString("Server_Url_Input", null), ignoreCase = true)) {
                                                networkType = "Internal"
                                                if (serverResponse.ServerDetailsList[0].NucleusWebAPIUrl.endsWith("/")) {
                                                    editor.apply {
                                                        putString("Nucleus_Base_Url", serverResponse.ServerDetailsList[0].NucleusWebAPIUrl + "API/")
                                                        commit()
                                                    }
                                                } else {
                                                    editor.apply {
                                                        putString("Nucleus_Base_Url", serverResponse.ServerDetailsList[0].NucleusWebAPIUrl + "/API/")
                                                        commit()
                                                    }
                                                }
                                            }

                                            if (serverResponse.ServerDetailsList[0].AtomServiceExternalURL.equals(sf.getString("Server_Url_Input", null), ignoreCase = true)) {
                                                networkType = "External"
                                                if (serverResponse.ServerDetailsList[0].WebAPIExternalUrl.endsWith("/")) {
                                                    editor.apply { putString("Nucleus_Base_Url", serverResponse.ServerDetailsList[0].WebAPIExternalUrl + "API/")
														commit()
													}
                                                } else {
                                                    editor.apply {
                                                        putString("Nucleus_Base_Url", serverResponse.ServerDetailsList[0].WebAPIExternalUrl + "/API/")
                                                        commit()

                                                    }

                                                }
                                            }

                                            _urlResponseLiveData.postValue(Resource.Success(serverResponse))

                                        } else {
                                            val errorMsg = serverResponse.ErrorMsg[0]
                                            Toast.makeText(MyApplication.appContext, errorMsg, Toast.LENGTH_LONG).show()
                                            _urlResponseLiveData.postValue(Resource.Failure(false, 0, serverResponse.ErrorMsg))
                                        }
                                    } else {
                                        Toast.makeText(MyApplication.appContext, "Empty Server Details", Toast.LENGTH_LONG).show()
                                        _urlResponseLiveData.postValue(Resource.Failure(false, 0, serverResponse.ErrorMsg))
                                    }
                                }
                            else{
                                        _urlResponseLiveData.postValue(Resource.Failure(false, 0, testMsg3))

                                }
                            }

                        override fun onFailure(call: Call<ServerDetailsResponse>, t: Throwable) {
                            _urlResponseLiveData.postValue(Resource.Failure(false, 0, testMsg1))
                            Toast.makeText(MyApplication.appContext, t.localizedMessage, Toast.LENGTH_LONG).show()
                        }
                    }
                )
        }
    }
    }
