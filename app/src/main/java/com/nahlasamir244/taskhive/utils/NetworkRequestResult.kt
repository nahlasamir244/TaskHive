package com.nahlasamir244.taskhive.utils

//utility class to hold the request data with UI state 
sealed class NetworkRequestResult<out R> {
    data class Success<T>(var data: T?) : NetworkRequestResult<T>()
    data class Error<T>(val exception: Exception, val message: String?,var data:T?) :
        NetworkRequestResult<T>()
    data class Loading<T>(val data: T?) : NetworkRequestResult<T>()
    //to be included to support online/offline UI status
    //    data class Offline<out T>(val data: T) : NetworkRequestResult<T>()

}