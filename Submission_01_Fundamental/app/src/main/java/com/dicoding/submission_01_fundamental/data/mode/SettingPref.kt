package com.dicoding.submission_01_fundamental.data.mode

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")
class Settings private constructor(private val dataStore: DataStore<Preferences>){

    companion object {
        @Volatile
        private var INSTANCE: Settings? = null

        fun getInstance(dataStore: DataStore<Preferences>): Settings {
            return INSTANCE?: synchronized(this) {
                val instance = Settings(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

    private val THEME_MODE = booleanPreferencesKey("theme_mode")

    fun getThemeMode(): Flow<Boolean> {
        return  dataStore.data.map { preferences ->
            preferences[THEME_MODE] ?: false
        }
    }

    suspend fun saveTheme(isDarkkmodeActive: Boolean) {
        dataStore.edit { preferences->
            preferences[THEME_MODE] = isDarkkmodeActive
        }
    }
}