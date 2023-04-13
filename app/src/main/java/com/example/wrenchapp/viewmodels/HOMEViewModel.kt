package com.example.wrenchapp.viewmodels

import android.content.SharedPreferences
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wrenchapp.MyApplication
import com.example.wrenchapp.datamodel.LogoutRequest
import com.example.wrenchapp.datamodel.LogoutResponse
import com.example.wrenchapp.datamodel.ServerDetailsResponse
import com.example.wrenchapp.network.AtomApiInterface
import com.example.wrenchapp.network.RetrofitInstance
import com.example.wrenchapp.network.RetrofitInstance.Companion.getAtomRetrofitInstance
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
}