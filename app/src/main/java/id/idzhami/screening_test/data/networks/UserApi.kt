package id.idzhami.screening_test.data.networks

import id.idzhami.screening_test.data.responses.UserResponses
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Usman idzhami On 04/06/2022
 */

interface UserApi {
    @GET("api/users")
    suspend fun GET_USER(
        @Query("page") page: Int,
        @Query("per_page") size: Int,
    ): UserResponses
}