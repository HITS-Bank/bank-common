package ru.hitsbank.bank_common.data.datasource

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.flow.map
import ru.hitsbank.bank_common.domain.entity.ThemeEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeManager @Inject constructor(
    context: Context,
) {

    private companion object {
        const val THEME_PREFERENCES = "theme_preferences"
        const val THEME_KEY = "theme"
    }

    private val themeKey = stringPreferencesKey(THEME_KEY)
    private val preferences = PreferenceDataStoreFactory.create {
        context.preferencesDataStoreFile(THEME_PREFERENCES)
    }

    suspend fun setTheme(theme: ThemeEntity) {
        preferences.edit { preferences ->
            preferences[themeKey] = theme.name
        }
    }

    fun getThemeFlow() = preferences.data.map { preferences ->
        ThemeEntity.valueOf(preferences[themeKey] ?: ThemeEntity.SAME_AS_SYSTEM.name)
    }
}