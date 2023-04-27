package com.example.wrenchapp.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FolderStructure_LevelInfo {

    @SerializedName("FOLDER_ID")
    @Expose
    Integer FOLDER_ID;
    public Integer getFOLDER_ID(){return FOLDER_ID;}

    @SerializedName("FOLDER_NAME")
    @Expose
    String FOLDER_NAME;
    public String getFOLDER_NAME(){return FOLDER_NAME;}

    @SerializedName("CHILD_EXISTS")
    @Expose
    Integer CHILD_EXISTS;
    public Integer getCHILD_EXISTS(){return CHILD_EXISTS;}

    @SerializedName("PARENT_FOLDER_ID")
    @Expose
    Integer PARENT_FOLDER_ID;
    public Integer getPARENT_FOLDER_ID(){ return PARENT_FOLDER_ID;}

    @SerializedName("FOLDER_ACCESS_TYPE")
    @Expose
    Integer FOLDER_ACCESS_TYPE;
    public Integer getFOLDER_ACCESS_TYPE(){return FOLDER_ACCESS_TYPE;}

    @SerializedName("OBJECT_TYPE")
    @Expose
    Integer OBJECT_TYPE;

    public Integer getOBJECT_TYPE() {return OBJECT_TYPE;}
}
