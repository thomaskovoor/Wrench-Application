package com.example.wrenchapp.datamodel

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class DataResponse {
    @SerializedName("DataList")
    @Expose
    private var dataList: DataList? = null

    fun getDataList(): DataList? {
        return dataList
    }

    fun setDataList(dataList: DataList?) {
        this.dataList = dataList
    }

    @SerializedName("ProcessDetails")
    @Expose
    private val processDetails: List<ProcessDetail>? = null

    fun getProcessDetails(): List<ProcessDetail>? {
        return processDetails
    }


}