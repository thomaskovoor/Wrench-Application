package com.example.wrenchapp.viewmodels

import android.content.SharedPreferences
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wrenchapp.MyApplication
import com.example.wrenchapp.datamodel.*
import com.example.wrenchapp.network.AtomApiInterface
import com.example.wrenchapp.network.NucleusApiInterface
import com.example.wrenchapp.network.RetrofitInstance
import com.example.wrenchapp.network.RetrofitInstance.Companion.getAtomRetrofitInstance
import com.example.wrenchapp.network.RetrofitInstance.Companion.getNucleusRetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HOMEViewModel : ViewModel() {

    private lateinit var sf : SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    var logoutStatus = false



    fun validateLogout(){

        sf = MyApplication.appContext.getSharedPreferences("my_sf_folder", AppCompatActivity.MODE_PRIVATE)
        editor=sf.edit()

        val loginToken = sf.getString("Login_Token",null)
        val request : LogoutRequest = LogoutRequest(loginToken!!)
         viewModelScope.launch {
             val apiInterface = getAtomRetrofitInstance()?.create(AtomApiInterface::class.java)
             apiInterface?.userLogout(request)?.enqueue(
                 object : Callback<LogoutResponse> {
                     override fun onResponse(
                         call: Call<LogoutResponse>,
                         response: Response<LogoutResponse>
                     ) {
                         if (response.isSuccessful) {
                             val logoutResponse: LogoutResponse = response.body()!!
                             if (logoutResponse.ErrorMsg == null) {
                                 logoutStatus = true
                             } else {
                                 Toast.makeText(MyApplication.appContext, "Empty Response", Toast.LENGTH_LONG).show()
                             }
                         }
                     }

                     override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                         Toast.makeText(MyApplication.appContext, t.localizedMessage, Toast.LENGTH_LONG).show()
                     }
                 }
             )
    }
}

    fun getUserList(){

        sf = MyApplication.appContext.getSharedPreferences("my_sf_folder", AppCompatActivity.MODE_PRIVATE)
        editor=sf.edit()
        val loginToken = sf.getString("Login_Token",null)
        val filterCriteria = listOf(FilterCriteria("","",0,1,"UM.USER_ID","101160",1))
        val objectReq = listOf(ObjectPropertyX("USER_PROPERTIES",2))

        val request = UserListReq(loginToken!!,filterCriteria,objectReq)
        viewModelScope.launch {
            val apiInterface = getNucleusRetrofitInstance()?.create(NucleusApiInterface::class.java)
            apiInterface?.getUserList(request)?.enqueue(
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