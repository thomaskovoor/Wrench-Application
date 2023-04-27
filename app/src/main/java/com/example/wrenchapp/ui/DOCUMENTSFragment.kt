package com.example.wrenchapp.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wrenchapp.MyApplication
import com.example.wrenchapp.R
import com.example.wrenchapp.adapters.DocumentsAdapter
import com.example.wrenchapp.datamodel.Resource
import com.example.wrenchapp.datamodel.SmartFolderDocDetail
import com.example.wrenchapp.elements.CustomProgressBar
import com.example.wrenchapp.viewmodels.DOCUMENTSViewModel


class DOCUMENTSFragment : Fragment() {

    private lateinit var sf : SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    lateinit var recyclerAdapter: DocumentsAdapter
    private var dialog: CustomProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_d_o_c_u_m_e_n_t_s, container, false)
        dialog = CustomProgressBar(activity)
        val viewModel = ViewModelProvider(this)[DOCUMENTSViewModel::class.java]

        val recyclerView = view.findViewById<RecyclerView>(R.id.document_rec_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerAdapter = DocumentsAdapter(MyApplication.appContext)
        recyclerView.adapter = recyclerAdapter
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
        val docID = arguments?.getInt("DocID")
        viewModel.getSmartFolderRuleCriteria(docID)
        var visibleItemCount: Int
        var totalItemCount : Int
        var pastVisibleItems : Int
        var loading = true
       /*recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager!!.childCount
                    totalItemCount = layoutManager.itemCount
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                    if (loading) {
                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                            loading = false
                            if (recyclerAdapter.countOfShowing < recyclerAdapter.allChallenges.size()) {
                                Log.e("...", "Last Item Wow !")
                                recyclerAdapter.increaseCountOfShowing()
                                recyclerAdapter.notifyDataSetChanged()
                            }
                            loading = true
                            viewModel.getSmartFolderDocDetails(docID!!)

                        }
                    }
                }
            }
        })*/




        viewModel.DocDetailsResponseLiveData.observe(viewLifecycleOwner){result ->
            when(result){
                is Resource.Failure -> {
                    dialog!!.dismissDialog()
                    Toast.makeText(activity,"Cannot retrieve data", Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                    dialog!!.showDialog()
                }
                is Resource.Success ->{
                    dialog!!.dismissDialog()
                    recyclerAdapter.setDocuments(result.value.smartFolderDocDetails as MutableList<SmartFolderDocDetail>)
                    recyclerAdapter.notifyDataSetChanged()
                }
            }
        }



        return view
    }

  /*  override fun getChildFolders(folderDetails: FolderStructure_LevelInfo,isFolder : Boolean) {
        val viewModel = ViewModelProvider(this)[DOCUMENTSViewModel::class.java]
        viewModel.getSmartFolderDocDetails(folderDetails.foldeR_ID)
        TODO("Not yet implemented")
    }*/


}

