package com.radjamahesaw0054.belanjabijak.ui.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsPreferences(private val context: Context) {
    companion object {
        val FILTER_BULAN_KEY = stringPreferencesKey("filter_bulan")
        val IS_GRID_KEY = booleanPreferencesKey("is_grid")
        val IS_DARK_MODE_KEY = booleanPreferencesKey("is_dark_mode")
    }

    val getBulanFilter: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[FILTER_BULAN_KEY] ?: "2026-05"
    }

    suspend fun saveBulanFilter(bulan: String) {
        context.dataStore.edit { preferences ->
            preferences[FILTER_BULAN_KEY] = bulan
        }
    }

    val getLayoutStatus: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_GRID_KEY] ?: false
    }

    val saveLayoutStatus: suspend (Boolean) -> Unit = { isGrid ->
        context.dataStore.edit { preferences ->
            preferences[IS_GRID_KEY] = isGrid
        }
    }

    val getThemeStatus: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_DARK_MODE_KEY] ?: false // Default false (Light Mode)
    }

    suspend fun saveThemeStatus(isDarkMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_DARK_MODE_KEY] = isDarkMode
        }
    }
}