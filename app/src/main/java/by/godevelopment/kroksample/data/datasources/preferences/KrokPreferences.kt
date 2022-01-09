package by.godevelopment.kroksample.data.datasources.preferences

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class KrokPreferences @Inject constructor(@ApplicationContext appContext: Context) {

    private val sharedPreferences = appContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun setCurrentLanguage(keyLang: Int) {
        sharedPreferences.edit {
            putInt(PREF_CURRENT_LANG_KEY, keyLang)
        }
    }

    fun getCurrentLanguage(): Int = sharedPreferences.getInt(PREF_CURRENT_LANG_KEY, NO_PARTY_KEY)

    companion object {
        private const val PREFERENCE_NAME = "krok_pref"
        private const val PREF_CURRENT_LANG_KEY = "current_language"
        const val NO_PARTY_KEY = 3
    }
}