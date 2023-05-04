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
        var loadMore = true
        var i=2
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisiblePosition: Int = layoutManager!!.findLastCompletelyVisibleItemPosition()
                Log.d("Recycler-PastVisibleitems", lastVisiblePosition.toString())
                visibleItemCount = layoutManager.childCount;
                Log.d("Recycler-Visibleitemcount", visibleItemCount.toString())
                totalItemCount = layoutManager.itemCount;
                pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
                Log.d("Recycler-TotalItemCount", totalItemCount.toString())
                Log.d("Recycler-PastVisibleitems", pastVisibleItems.toString())
                if (lastVisiblePosition == totalItemCount-1) {
                    if (loadMore) {
                        viewModel.getSmartFolderDocDetails(docID!!, i++)
                        Log.d("Recycler-I", i.toString())
                    }
                }
            }
        })

      /*  recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager!!.childCount
                    totalItemCount = layoutManager.itemCount
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                    if (loadMore) {
                        loadMore = visibleItemCount + pastVisibleItems < totalItemCount
                        viewModel.getSmartFolderDocDetails(docID!!, viewModel.i+1)
                        }
                    }
                }

        })*/


        val dataList : MutableList<SmartFolderDocDetail> = ArrayList()

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
                    if(result.value.smartFolderDocDetails.isEmpty())
                        loadMore = false
                    dataList.addAll(result.value.smartFolderDocDetails as MutableList<SmartFolderDocDetail>)
                    recyclerAdapter.setDocuments(dataList)
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

