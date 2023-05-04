package com.example.wrenchapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.wrenchapp.R
import com.example.wrenchapp.datamodel.CommonService
import com.example.wrenchapp.datamodel.SmartFolderDocDetail
import com.example.wrenchapp.elements.RandomColors

class DocumentsAdapter(private val context : Context) : RecyclerView.Adapter<DocumentsAdapter.ViewHolder>() {
    private var documentsList:MutableList<SmartFolderDocDetail>? = null


    fun setDocuments(documentsList: MutableList<SmartFolderDocDetail>?){
      this.documentsList = documentsList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_item_documents,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val document = documentsList!![position]
        holder.bind(document)
        holder.downArrowBtn.setOnClickListener {
            if (holder.layout.visibility == View.VISIBLE) {
                holder.layout.visibility = View.GONE
                holder.downArrowBtn.setImageResource(R.drawable.down_arrow)
            }
            else{ (holder.layout.visibility == View.GONE)
                holder.layout.visibility = View.VISIBLE
                holder.downArrowBtn.setImageResource(R.drawable.upload)
            }
        }

    }

    override fun getItemCount(): Int {
        return if(documentsList == null) 0
        else
            documentsList!!.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val docName : TextView = view.findViewById(R.id.docName)
        val docIcon :TextView = view.findViewById(R.id.docIcon)
        val downArrowBtn : ImageView = view.findViewById(R.id.downArrow)
        val docDesc : TextView = view.findViewById(R.id.docInfo)
        val status : TextView = view.findViewById(R.id.status)
        val layout : LinearLayout = view.findViewById(R.id.list_Items_layout)
         val listDocName : TextView = view.findViewById(R.id.listDocName)
        val listDocDesc : TextView = view.findViewById(R.id.listDocDesc)
        val listRevisionNum : TextView = view.findViewById(R.id.listRevNum)
        val listProjectId : TextView = view.findViewById(R.id.listProjectId)
        val listCreatedDate : TextView = view.findViewById(R.id.listCreatedDate)
        val cardView : CardView = view.findViewById(R.id.cardView)
        private val randomColors = RandomColors()
        fun bind(data : SmartFolderDocDetail){
            cardView.setCardBackgroundColor(randomColors.color)
              docName.text = data.DocNumber
            docIcon.text = data.DocNumber.uppercase().subSequence(0,1)
            docDesc.text = data.DocDescr
            status.text = data.DocStatus
            if(data.DocStatus.equals("Work In Progress",ignoreCase = true)){
                status.setTextColor(status.context.getColor(R.color.doc_progress))
            }
            else if(data.DocStatus.equals("Issues",ignoreCase = true)){
                status.setTextColor(status.context.getColor(R.color.doc_issued))
            }
            else{
                status.setTextColor(status.context.getColor(R.color.doc_released))
            }

            listDocName.text = data.DocNumber
            listDocDesc.text = data.DocDescr
            listRevisionNum.text = data.RevisionNumber
            listProjectId.text = data.ProjectId
            val date : String = data.CreatedOn
            val dateConversion = CommonService()
            listCreatedDate.text = dateConversion.DateParsor(date)
        }
    }
}