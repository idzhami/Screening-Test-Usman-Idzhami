package id.idzhami.screening_test.data.networks

import android.content.Context
import android.util.Log
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.squareup.picasso.BuildConfig
import id.idzhami.screening_test.utils.UserPreferences
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
/**
 * Created by Usman idzhami On 04/06/2022
 */
class RemoteDataSource {
    companion object {
        private var BASE_URL = "https://reqres.in/"
    }

    fun <Api> buildApi(
        context: Context,
        api: Class<Api>,
        pref: UserPreferences
    ): Api {
        try {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(
                    OkHttpClient.Builder()
                        .also { client ->
                            if (BuildConfig.DEBUG) {
                                val logging = HttpLoggingInterceptor()
                                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                                client.addInterceptor(logging)
                                Log.d("message", client.addInterceptor(logging).toString())
                            }
                        }.addInterceptor(ChuckerInterceptor(context)).build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(api)
        } catch (e: Exception) {
            throw RuntimeException(e);
        }
    }

}