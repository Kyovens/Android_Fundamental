package com.dicoding.submission_01_fundamental.data.local


sealed class Result<out R> private constructor(){
    data class Successful<out T>(val data: T): Result<T>()
    data class Error(val error: String) : Result<Nothing>()
    object Load : Result<Nothing>()
    object Empty : Result<Nothing>()
}