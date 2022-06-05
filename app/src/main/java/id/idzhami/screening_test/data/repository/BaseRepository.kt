package id.idzhami.screening_test.data.repository


import id.idzhami.screening_test.data.networks.Resource
import id.idzhami.screening_test.utils.UserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

/**
 * Created by Usman idzhami On 04/06/2022
 */

abstract class BaseRepository(
    protected val preferences: UserPreferences
) {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        Resource.Failure(
                            isNetworkError = false,
                            throwable.code(),
                            throwable.response()?.errorBody(),
                        )
                    }
                    else -> {
                        Resource.Failure(
                            isNetworkError = true,
                            errorCode = null,
                            errorBody = null,
                        )
                    }
                }
            }

        }
    }

}