package com.example.wrenchapp.interfaces

import com.example.wrenchapp.datamodel.BreadCrumb
import com.example.wrenchapp.datamodel.FolderStructure_LevelInfo

interface FolderInterface {

    fun getChildFolders(folderDetails :FolderStructure_LevelInfo,isFolder:Boolean)

    fun getChildFolderBreadCrumbs(folderList : BreadCrumb,pos :Int)


}