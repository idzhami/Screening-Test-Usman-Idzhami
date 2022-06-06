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

    suspend fun saveImage(imageEncoded: String) {
        dataStore.edit { preferences ->
            preferences[KEY_IMAGE] = imageEncoded
        }
    }

    fun getImage(): String? {
        return runBlocking { image.first() }
    }

    val image : Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_IMAGE]
        }

    suspend fun saveName(Name: String) {
        dataStore.edit { preferences ->
            preferences[KEY_NAME] = Name
        }
    }

    fun getName(): String? {
        return runBlocking { name.first() }
    }

    val name : Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_NAME]
        }

    suspend fun clear() {
        dataStore.edit { preferences ->
            run {
                preferences.remove(KEY_IMAGE)
            }

        }
    }

    companion object {
        private val KEY_IMAGE = preferencesKey<String>(name = "key_image")
        private val KEY_NAME = preferencesKey<String>(name = "key_name")
    }
}
