package com.example.wrenchapp.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Login_ProcessDetails {


        @SerializedName("ProcessId")
        @Expose
        private Integer ProcessId;
        @SerializedName("ProcessDesc")
        @Expose
        private String ProcessDesc;
        @SerializedName("ProcessStatus")
        @Expose
        private Integer ProcessStatus;
        @SerializedName("ObjectType")
        @Expose
        private Integer ObjectType;
        @SerializedName("ObjectId")
        @Expose
        private Integer ObjectId;
        @SerializedName("ObjectDesc")
        @Expose
        private String ObjectDesc;
        @SerializedName("ErrorDetails")
        @Expose
        private List<String> ErrorDetails;

        public List<String> getErrorDetails(){return ErrorDetails;}
        public void setErrorDetails(List<String> val){ErrorDetails = val;}


    }

