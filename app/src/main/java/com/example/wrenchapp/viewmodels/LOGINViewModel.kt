
@file:Suppress("SpellCheckingInspection", "SpellCheckingInspection", "SpellCheckingInspection",
    "SpellCheckingInspection", "SENSELESS_COMPARISON", "ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE",
    "LocalVariableName", "unused"
)
package com.example.wrenchapp.viewmodels
import android.content.SharedPreferences
import android.widget.Toast
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

@Suppress("PropertyName", "SpellCheckingInspection")
class LOGINViewModel : ViewModel() {

    var _loginResponseLiveData = MutableLiveData<Resource<DataResponse>>()
    val loginResponseLiveData: LiveData<Resource<DataResponse>> get() = _loginResponseLiveData
    var emaillogin:Int = 0
    var mobilelogin:Int = 0
    var authenticationMode = 1
    val testMsg1 = listOf("Network Error")
    val testMsg2 = listOf("Input Details Error")
    val testMsg3 = listOf("Response Error")
    private lateinit var sf : SharedPreferences
    private lateinit var editor: SharedPreferences.Editor




    fun validateControlSetting()
    {

         sf = MyApplication.appContext.getSharedPreferences("my_sf_folder", AppCompatActivity.MODE_PRIVATE)
        val secretKey = "berDm70gXAkpa32r/g4o0raCYg4SgrCncsoEvz+fWhJd/29pSFvP4CL+yQDFDCn/"
        val controlRequest = ControlSettingRequest(sf.getString("Base_Url",null),secretKey)
        viewModelScope.launch {
            val apiInterface = getAtomRetrofitInstance()?.create(AtomApiInterface::class.java)
            apiInterface?.getControlSettings(controlRequest)?.enqueue(
                object : Callback<ControlSettingResponse> {
                    override fun onResponse(
                        call: Call<ControlSettingResponse>,
                        response: Response<ControlSettingResponse>
                    ) {
                            if (response.isSuccessful) {
                                val controlResponse: ControlSettingResponse = response.body()!!
                                if (controlResponse.ErrorMsg == null) {
                                    if(controlResponse.EmailLoginEnabled == 1)
                                        emaillogin = 1
                                    if(controlResponse.PhoneLoginEnabled == 1)
                                        mobilelogin = 1
                                }
                                else {
                                    Toast.makeText(MyApplication.appContext, "Response Error", Toast.LENGTH_SHORT).show()
                                    _loginResponseLiveData.postValue(Resource.Failure(false,0, listOf(controlResponse.ErrorMsg)))
                            }
                    }
                            else{
                                Toast.makeText(MyApplication.appContext, "Empty Response", Toast.LENGTH_SHORT).show()
                                _loginResponseLiveData.postValue(Resource.Failure(false,0, testMsg3))
                            }
                    }
                    override fun onFailure(call: Call<ControlSettingResponse>, t: Throwable) {
                        Toast.makeText(MyApplication.appContext,t.localizedMessage, Toast.LENGTH_SHORT).show()
                        _loginResponseLiveData.postValue(Resource.Failure(false,0, testMsg1))
                    }
                }
            )
        }
    }


    fun validateGuestLogin(userName: String)
    {


        var token:String
        val HAND_SHAKE_CODE= "VA9L9gwb1LGoX9/NlnLi3eMswgoLPv7y1NOiyaSe9DXVRgbE2hlGuJI2Mzxgu6vX/6XGs3+gZChorLFNhlNc16KFc3U4l0ePebwIO+ACuxvYQVRpQ16+Ejly+o2d2Sn2c+qyd+OlQXhtJbrzUXLsB1dP6bwWDH32jMZcSp6WuI1UU6QCei1iScKRxOCcrs0begfxhaGlFP3Y/UHk35ac99BqgvZPCTcDwwjdwSx6TSPzZtJHtNwiXJwibpCjYhMmPxL84FqvuaLKHUwTV/W/6VZUIxiwQ5O+kXvDrPRCe5lQxqki1BXnI/sQiT4I1Srk"
        val guestLoginRequest = GuestLoginRequest()
        guestLoginRequest.HAND_SHAKE_CODE = HAND_SHAKE_CODE
        viewModelScope.launch {
            val apiInterface = getNucleusRetrofitInstance()?.create(NucleusApiInterface::class.java)
            apiInterface?.getGuestLogin(guestLoginRequest)?.enqueue(
                object : Callback<DataResponse>{
                    override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>
                    ) {

                            if(response.isSuccessful){
                                val userDetails: List<GuestLogin_Login>?
                                val data: DataResponse = response.body()!!
                                val commonHelper = CommonService()
                                userDetails = if (data.getDataList()!!.getGuestLoginDetails() != null) {
                                    if (data.getDataList()!!.getGuestLoginDetails()!!.isNotEmpty()) {
                                        val guestLogin = GuestLogin_Login()
                                        commonHelper.parseNucleusData(
                                            data.getDataList()?.getGuestLoginDetails(),
                                            guestLogin
                                        ) as List<GuestLogin_Login>
                                    } else {
                                        ArrayList()
                                    }
                                } else {
                                    ArrayList()
                                }
                                if(data.getProcessDetails()?.get(0)!!.ErrorDetails.isEmpty())
                                {
                                    token = userDetails[0].token
                                    validateGetLoginPrerequisites(token,userName)
                                }
                                else{
                                    Toast.makeText(MyApplication.appContext,"Invaid Username",Toast.LENGTH_SHORT).show()
                                    _loginResponseLiveData.postValue(Resource.Failure(false,0,data.getProcessDetails()?.get(0)!!.ErrorDetails))
                                }
                            }
                        else{
                                Toast.makeText(MyApplication.appContext,"Empty Response",Toast.LENGTH_SHORT).show()
                                _loginResponseLiveData.postValue(Resource.Failure(false,0,testMsg3))

                            }

                    }

                    override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                        Toast.makeText(MyApplication.appContext,t.localizedMessage, Toast.LENGTH_SHORT).show()
                        _loginResponseLiveData.postValue(Resource.Failure(false,0,testMsg1))
                    }

                }
            )
        }


    }

     fun validateGetLoginPrerequisites(token: String, userName: String) {

        val dataList = mutableListOf<ObjectProperty>()
         dataList.add(ObjectProperty("LOGIN_NAME",userName))

         val loginPrerequisitesReq = LoginPrerequisitesRequest(token,dataList)

         viewModelScope.launch {
             val apiInterface = getNucleusRetrofitInstance()?.create(NucleusApiInterface::class.java)
             apiInterface?.getLoginPrerequisites(loginPrerequisitesReq)?.enqueue(
                 object : Callback<DataResponse> {
                     override fun onResponse(
                         call: Call<DataResponse>,
                         response: Response<DataResponse>
                     ) {
                             if (response.isSuccessful) {
                                 val userDetails: List<LoginPrerequisites_UserDetails>?
                                 val data: DataResponse = response.body()!!
                                 val commonHelper = CommonService()
                                 userDetails = if (data.getDataList()!!.getUserDetails() != null) {
                                     if (data.getDataList()!!.getUserDetails()!!.isNotEmpty()) {
                                         val loginPrerequisitesUserDetails = LoginPrerequisites_UserDetails()
                                         commonHelper.parseNucleusData(
                                             data.getDataList()?.getUserDetails(),
                                             loginPrerequisitesUserDetails
                                         ) as List<LoginPrerequisites_UserDetails>
                                     } else {
                                         ArrayList()
                                     }
                                 } else {
                                     ArrayList()
                                 }
                                 if(data.getProcessDetails()!![0].ErrorDetails.isEmpty()){
                                     if(userDetails[0].authenticationMode == 6) {
                                         authenticationMode = 6
                                     }
                                 }
                                 else{
                                     Toast.makeText(MyApplication.appContext,"Response Error",Toast.LENGTH_SHORT).show()
                                     _loginResponseLiveData.postValue(Resource.Failure(false,0,data.getProcessDetails()!![0].ErrorDetails))
                                 }
                             }
                         else{
                                 Toast.makeText(MyApplication.appContext,"Empty Response",Toast.LENGTH_SHORT).show()
                                 _loginResponseLiveData.postValue(Resource.Failure(false,0,testMsg3))
                             }
                     }
                     override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                         Toast.makeText(MyApplication.appContext,t.localizedMessage, Toast.LENGTH_SHORT).show()
                         _loginResponseLiveData.postValue(Resource.Failure(false,0,testMsg1))
                     }
                 }
             )
         }
    }



    fun validateLogin(userName: String, password: String? )
    {

        _loginResponseLiveData.postValue(Resource.Loading)
        val loginReq = LoginRequest(5.0, 37.4219983,
            "1600 Amphitheatre Pkwy, Mountain View, CA 94043, USA",
            -122.084, 0, userName,
            password!!, 1, "Android",)

        sf = MyApplication.appContext.getSharedPreferences("my_sf_folder", AppCompatActivity.MODE_PRIVATE)
        editor=sf.edit()

        viewModelScope.launch {
            val apiInterface = getNucleusRetrofitInstance()?.create(NucleusApiInterface::class.java)
            apiInterface?.userLogin(loginReq)?.enqueue(
                object : Callback<DataResponse> {
                    override fun onResponse(
                        call: Call<DataResponse>,
                        response: Response<DataResponse>
                    ) {
                        if (response.isSuccessful) {
                            val userDetails: List<GuestLogin_Login>?
                            val data: DataResponse = response.body()!!
                            val commonHelper = CommonService()
                            userDetails = if (data.getDataList()!!.getGuestLoginDetails() != null) {
                                if (data.getDataList()!!.getGuestLoginDetails()!!.isNotEmpty()) {
                                    val guestLogin = GuestLogin_Login()
                                    commonHelper.parseNucleusData(
                                        data.getDataList()?.getGuestLoginDetails(),
                                        guestLogin
                                    ) as List<GuestLogin_Login>
                                } else {
                                    ArrayList()
                                }
                            } else {
                                ArrayList()
                            }

                            if (data.getProcessDetails()!![0].ErrorDetails.isEmpty()) {
                                Toast.makeText(MyApplication.appContext, "Login Successful", Toast.LENGTH_SHORT).show()
                                val loginToken = userDetails[0].token
                                editor.apply {
                                            putString("Login_Token",loginToken)
                                            commit()
                                    }
                              _loginResponseLiveData.postValue(Resource.Success(data))
                            }
                            else{
                                Toast.makeText(MyApplication.appContext,"Invalid Credentials", Toast.LENGTH_SHORT).show()
                                _loginResponseLiveData.postValue(Resource.Failure(false, 0,data.getProcessDetails()!![0].ErrorDetails ))
                            }
                        }
                        else{
                            Toast.makeText(MyApplication.appContext, "Empty Response", Toast.LENGTH_SHORT).show()
                            _loginResponseLiveData.postValue(Resource.Failure(false, 0, testMsg3))
                        }
                    }


                    override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                        Toast.makeText(MyApplication.appContext,t.localizedMessage,Toast.LENGTH_SHORT).show()
                        _loginResponseLiveData.postValue(Resource.Failure(false,0,testMsg1))
                    }
                }
            )
    }
}
}

