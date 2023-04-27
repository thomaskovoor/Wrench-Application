package com.example.wrenchapp

import com.example.wrenchapp.datamodel.FolderStructure_LevelInfo

interface FolderInterface {

    fun getChildFolders(folderDetails :FolderStructure_LevelInfo,isFolder:Boolean)


}