package com.example.wrenchapp.ui


import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.wrenchapp.MyApplication
import com.example.wrenchapp.R
import com.example.wrenchapp.datamodel.Resource
import com.example.wrenchapp.elements.CustomProgressBar
import com.example.wrenchapp.viewmodels.LOGINViewModel
import com.google.android.material.textfield.TextInputLayout


class LOGINFragment : Fragment() {


    private var dialog: CustomProgressBar? = null
    private lateinit var sf : SharedPreferences
    private lateinit var editor: SharedPreferences.Editor




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_l_o_g_i_n, container, false)


        val dialogBinding = layoutInflater.inflate(R.layout.error_dialog,null)
        val myDialog = Dialog(requireContext())
        myDialog.setContentView(dialogBinding)
        myDialog.setCancelable(false)
        myDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val viewModel = ViewModelProvider(this)[LOGINViewModel::class.java]
        dialog = CustomProgressBar(activity)
        val loginButton1 = view.findViewById<Button>(R.id.login_btn1)
        val loginUserName = view.findViewById<EditText>(R.id.login_name)
        val loginPassword = view.findViewById<EditText>(R.id.login_pssd)
        val loginBackButton = view.findViewById<TextView>(R.id.login_back)
        val signInCheckBox = view.findViewById<CheckBox>(R.id.login_checkbox)
        val rememberCheckBox = view.findViewById<CheckBox>(R.id.remember_checkbox)
        val okButton = dialogBinding.findViewById<Button>(R.id.closeBtn)



        signInCheckBox.visibility = View.INVISIBLE
        loginPassword.visibility = View.GONE
        viewModel.validateControlSetting()
        loginButton1.text = "Continue"
        rememberCheckBox.visibility = View.VISIBLE




        sf = MyApplication.appContext.getSharedPreferences(
            "my_sf_folder",
            AppCompatActivity.MODE_PRIVATE
        )
        editor = sf.edit()

        val rememberCheckBoxValue = sf.getBoolean("isRememberChecked", false)
        if (rememberCheckBoxValue) {
            loginUserName.setText(sf.getString("RememberUsername", "").toString())
        }



        val textInputLayout: TextInputLayout?
        if (viewModel.mobilelogin == 1 && viewModel.emaillogin == 1) {
             textInputLayout = view.findViewById(R.id.text_ip_layout)
            textInputLayout!!.hint = "Login Name\\Email\\Mobile Number"
        } else if (viewModel.emaillogin == 1) {
             textInputLayout = view.findViewById(R.id.text_ip_layout)
            textInputLayout!!.hint = "Login name".plus("\\Email")
        } else if (viewModel.mobilelogin == 1) {
             textInputLayout = view.findViewById(R.id.text_ip_layout)
            textInputLayout!!.hint = "Login Name".plus("\\Mobile Number")
        }



        loginBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_LOGINFragment_to_URLFragment)
        }


        loginButton1.setOnClickListener {
            if (loginButton1.text.toString() == "Continue") {
                val userName = loginUserName.text.toString()
                if (userName.isEmpty()) {
                    Toast.makeText(activity, "Please Enter UserName", Toast.LENGTH_LONG).show()
                } else {
                    viewModel.validateGuestLogin(userName)
                    if (viewModel.authenticationMode == 6) {
                        Toast.makeText(
                            activity,
                            "Single sign on feature not implemented in this app",
                            Toast.LENGTH_LONG
                        ).show()
                        loginUserName.isEnabled = false

                        loginPassword.visibility = View.GONE
                        loginButton1.text = "Login"
                    } else {
                        loginUserName.isEnabled = false
                        loginPassword.visibility = View.VISIBLE
                        loginButton1.text = "Login"
                        rememberCheckBox.visibility = View.GONE
                        signInCheckBox.visibility = View.VISIBLE


                    }
                }
            } else if (loginButton1.text.toString() == "Login") {

                viewModel.validateLogin(
                    loginUserName.text.toString(),
                    loginPassword.text.toString()
                )
            }
        }
        signInCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            // Toast.makeText(activity,isChecked.toString(),Toast.LENGTH_SHORT).show()
            if (isChecked) {
                editor.apply {
                    putString("Username", loginUserName.text.toString())
                    putString("Password", loginPassword.text.toString())
                    putBoolean("isChecked", true)
                    commit()
                }
            } else {
                editor.apply {
                    putBoolean("isChecked", false)
                    commit()
                }
            }
        }
        rememberCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            // Toast.makeText(activity,isChecked.toString(),Toast.LENGTH_SHORT).show()
            if (isChecked) {
                editor.apply {
                    putString("RememberUsername", loginUserName.text.toString())
                    putBoolean("isRememberChecked", true)
                    commit()
                }
            } else {
                editor.apply {
                    putBoolean("isRememberChecked", false)
                    commit()
                }
            }

        }


           okButton.setOnClickListener {
                myDialog.dismiss()
             }

        viewModel.loginResponseLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Failure -> {
                    dialog!!.dismissDialog()
                    myDialog.findViewById<TextView>(R.id.showMsg).text = result.errorBody.toString()
                    myDialog.show()


                }
                is Resource.Loading -> {
                    dialog!!.showDialog()
                }
                is Resource.Success -> {
                    dialog!!.dismissDialog()
                    findNavController().navigate(R.id.action_LOGINFragment_to_HOMEFragment)
                }

            }
        }


        val fm: FragmentManager = requireActivity().supportFragmentManager
        val count: Int = fm.backStackEntryCount
        Log.d("Stack", "$count")
        for (i in 0 until count) {
            fm.popBackStackImmediate()
        }

            val callback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    //  findNavController().popBackStack()
                    minimizeApp()
                }
            }
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)



            return view
        }


    fun minimizeApp() {
        val startMain = Intent(Intent.ACTION_MAIN)
        startMain.addCategory(Intent.CATEGORY_HOME)
        startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(startMain)
    }

}