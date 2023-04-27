package com.example.wrenchapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wrenchapp.FolderInterface
import com.example.wrenchapp.R
import com.example.wrenchapp.datamodel.FolderStructure_LevelInfo


class FoldersAdapter(private val context : Context) : RecyclerView.Adapter<FoldersAdapter.ViewHolder>()
     {

    private var foldersList:MutableList<FolderStructure_LevelInfo>? = null
         private lateinit var recyclerListener : FolderInterface

    fun setFolders(foldersList: MutableList<FolderStructure_LevelInfo>?,recyclerListener : FolderInterface){
        this.foldersList = foldersList
        this.recyclerListener = recyclerListener
    }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoldersAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_item_folders,parent,false)
       return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoldersAdapter.ViewHolder, position: Int) {
        val folder = foldersList!![position]
        var isFolder: Boolean
        holder.bind(folder)
        holder.area.setOnClickListener {
            isFolder = true
            if(folder.chilD_EXISTS == 0 ) {
                isFolder = false
            }
            foldersList!!.clear()

            recyclerListener.getChildFolders(folder,isFolder)
        }

        holder.arrowButton.setOnClickListener{
            isFolder = false
            recyclerListener.getChildFolders(folder,isFolder)
        }
    }

    override fun getItemCount(): Int {
        return if(foldersList == null) 0
        else
            foldersList!!.size
    }


    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){

        private val folderName: TextView = itemView.findViewById(R.id.folderName)
         val arrowButton : ImageView = itemView.findViewById(R.id.arrowBtn)
        val area : LinearLayout = itemView.findViewById(R.id.linear_layout)
         private val folderImage :ImageView = itemView.findViewById(R.id.folderImg)

        fun bind(data : FolderStructure_LevelInfo){
            folderName.text =data.foldeR_NAME.toString()
            if(data.chilD_EXISTS == 1){ arrowButton.visibility = View.VISIBLE }
            else
            {
                arrowButton.visibility = View.GONE
                folderImage.setImageResource(R.drawable.folder)
            }
            if(data.foldeR_ACCESS_TYPE == 1){
                   folderName.setTextColor(folderName.context.getColor(R.color.home))
            }else {
                folderName.setTextColor(folderName.context.getColor(R.color.black))
            }
        }

    }
     }