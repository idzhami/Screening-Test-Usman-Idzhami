package id.idzhami.screening_test.utils

import android.content.Context
import androidx.annotation.Keep
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

@Keep
class UserPreferences(
    context: Context,
) {
    private val applicationContext = context.applicationContext
    private val dataStore: DataStore<Preferences>

    init {
        dataStore = applicationContext.createDataStore(
            name = "my_data_store"
        )
    }

    suspend fun saveFirstLogin(value: String) {
        dataStore.edit { preferences ->
            preferences[KEY_FIRST] = value
        }
    }

    fun getFirstLogin(): String? {
        return runBlocking { IdFirstLogin.first() }
    }

    val IdFirstLogin: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_FIRST]
        }


    suspend fun clear() {
        dataStore.edit { preferences ->
            run {
                preferences.remove(KEY_FIRST)
            }

        }
    }

    companion object {
        private val KEY_FIRST = preferencesKey<String>(name = "key_first")
    }
}
