package com.example.wrenchapp.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Common_Format {
    @SerializedName( "FieldName" )
    @Expose
    private String FieldName;
    @SerializedName( "ValueType" )
    @Expose
    private Integer ValueType;
    @SerializedName( "Value" )
    @Expose
    private String value;

    public String getFieldName() {
        return FieldName;
    }

    public void setFieldName(String fieldName) {
        FieldName = fieldName;
    }

    public Integer getValueType() {
        return ValueType;
    }

    public void setValueType(Integer valueType) {
        ValueType = valueType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}