package com.example.wrenchapp.datamodel

sealed class Resource<out T> {
    data class Success<out T>(val value: T) : Resource<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: List<String>?    ) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}