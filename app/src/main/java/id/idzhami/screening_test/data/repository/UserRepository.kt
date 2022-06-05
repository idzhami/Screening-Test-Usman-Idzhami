package id.idzhami.screening_test.data.repository

import id.idzhami.screening_test.data.networks.UserApi
import id.idzhami.screening_test.utils.UserPreferences
/**
 * Created by Usman idzhami On 04/06/2022
 */
class UserRepository(
    private val api: UserApi,
    private val pre: UserPreferences
) : BaseRepository(pre) {
    suspend fun GET_USER(
        page: Int,
        size: Int,
    ) = safeApiCall {
        api.GET_USER(page,size)
    }
}