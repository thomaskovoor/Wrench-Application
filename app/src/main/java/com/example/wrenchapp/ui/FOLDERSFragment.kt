package com.example.wrenchapp.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wrenchapp.FolderInterface
import com.example.wrenchapp.MyApplication
import com.example.wrenchapp.R
import com.example.wrenchapp.adapters.FoldersAdapter
import com.example.wrenchapp.datamodel.FolderStructure_LevelInfo
import com.example.wrenchapp.datamodel.Resource
import com.example.wrenchapp.elements.CustomProgressBar
import com.example.wrenchapp.viewmodels.FOLDERSViewModel


class FOLDERSFragment : Fragment(),FolderInterface{

    private lateinit var sf : SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    lateinit var recyclerAdapter: FoldersAdapter
    private var dialog: CustomProgressBar? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_f_o_l_d_e_r_s, container, false)
        dialog = CustomProgressBar(activity)
         val viewModel = ViewModelProvider(this)[FOLDERSViewModel::class.java]



        val recyclerView = view.findViewById<RecyclerView>(R.id.folder_rec_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerAdapter = FoldersAdapter(MyApplication.appContext)
        recyclerView.adapter = recyclerAdapter

        viewModel.getFolderStructure()


            viewModel.FolderStructResponseLiveData.observe(viewLifecycleOwner) { result ->
                when (result) {

                    is Resource.Failure -> {
                        dialog!!.dismissDialog()
                        Toast.makeText(activity,"Cannot retrieve data",Toast.LENGTH_LONG).show()
                    }
                    is Resource.Loading -> {
                        dialog!!.showDialog()
                    }
                    is Resource.Success -> {
                        dialog!!.dismissDialog()
                        recyclerAdapter.setFolders(result.value as MutableList<FolderStructure_LevelInfo>?,this)
                        recyclerAdapter.notifyDataSetChanged()
                    }
                }
            }

            viewModel.FolderDetailsResponseLiveData.observe(viewLifecycleOwner){result ->
                when(result){
                    is Resource.Failure->{
                        dialog!!.dismissDialog()
                        Toast.makeText(activity,"Cannot retrieve data",Toast.LENGTH_LONG).show()
                    }
                    is Resource.Loading ->{
                        dialog!!.showDialog()
                    }
                    is Resource.Success ->{
                        dialog!!.dismissDialog()
                        recyclerAdapter.setFolders(result.value as MutableList<FolderStructure_LevelInfo>?,this)
                        recyclerAdapter.notifyDataSetChanged()
                    }
                }
            }

            return view
        }

    override fun getChildFolders(folderDetails : FolderStructure_LevelInfo,isFolder : Boolean) {
        val bundle = Bundle()
        val viewModel = ViewModelProvider(this)[FOLDERSViewModel::class.java]
         //Toast.makeText(MyApplication.appContext,"${folderDetails.foldeR_ID}",Toast.LENGTH_SHORT).show()0
        if(isFolder) {
            viewModel.getPersonalFolderDetails(folderDetails.foldeR_ID)
        }
        else{
            bundle.putInt("DocID", folderDetails.foldeR_ID)
            val destinationFragment = DOCUMENTSFragment()
            destinationFragment.arguments = bundle
            findNavController().navigate(R.id.action_FOLDERSFragment_to_DOCUMENTSFragment,bundle)
        }
    }
}


