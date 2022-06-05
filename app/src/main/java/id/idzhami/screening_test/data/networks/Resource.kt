package id.idzhami.screening_test.data.networks

import okhttp3.ResponseBody
/**
 * Created by Usman idzhami On 04/06/2022
 */
sealed class Resource<out T>{
    data class Success<out T>(val value: T): Resource<T>()
    data class  Failure(
        val isNetworkError : Boolean,
        val errorCode :Int?,
        val errorBody: ResponseBody?
    ): Resource<Nothing>()
    object Loading : Resource<Nothing>()

}