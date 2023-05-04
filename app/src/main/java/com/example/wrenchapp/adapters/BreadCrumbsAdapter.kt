package com.example.wrenchapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wrenchapp.R
import com.example.wrenchapp.datamodel.BreadCrumb
import com.example.wrenchapp.interfaces.FolderInterface

class BreadCrumbsAdapter(private val context : Context) : RecyclerView.Adapter<BreadCrumbsAdapter.ViewHolder>() {

    private var foldersList:MutableList<BreadCrumb>? = null
    private lateinit var recyclerListener : FolderInterface
    var pos = -1
    fun setPath(foldersList : MutableList<BreadCrumb>,recyclerListener : FolderInterface) {
        this.foldersList = foldersList
        this.recyclerListener = recyclerListener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreadCrumbsAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_item_breadcrumb,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: BreadCrumbsAdapter.ViewHolder, position: Int) {
        val folder = foldersList!![position]
        var isLast : Boolean = false

        holder.pathName.setOnClickListener{
            pos = position
            if(pos == foldersList!!.size-1)
                isLast = true
recyclerListener.getChildFolderBreadCrumbs(folder,pos)
        }
        holder.bind(folder,isLast)
    }

    override fun getItemCount(): Int {
        return if(foldersList == null) 0
        else
            foldersList!!.size
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        val pathName : TextView = view.findViewById(R.id.path)
        private val pointerIcon : TextView = view.findViewById(R.id.pointer)
        fun bind(data : BreadCrumb,isLast : Boolean){
            pathName.text = data.folderName
            if(isLast){
                pointerIcon.visibility = View.GONE
            }
        }
    }


}