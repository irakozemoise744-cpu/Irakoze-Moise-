package com.example.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.i18n.AppLanguage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.mozzoPrefsDataStore: DataStore<Preferences> by preferencesDataStore(name = "mozzo_preferences")

class LanguageRepository(private val context: Context) {
    private val languageKey = stringPreferencesKey("app_language")

    val selectedLanguage: Flow<AppLanguage> = context.mozzoPrefsDataStore.data.map { prefs ->
        when (prefs[languageKey]) {
            AppLanguage.RW.code -> AppLanguage.RW
            else -> AppLanguage.EN
        }
    }

    suspend fun setLanguage(language: AppLanguage) {
        context.mozzoPrefsDataStore.edit { prefs ->
            prefs[languageKey] = language.code
        }
    }
}
