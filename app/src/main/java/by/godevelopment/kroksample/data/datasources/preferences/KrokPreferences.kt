package by.godevelopment.kroksample.data.datasources.preferences

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class KrokPreferences @Inject constructor(
    @ApplicationContext appContext: Context
) {
    private val sharedPreferences = appContext.getSharedPreferences(
        PREFERENCE_NAME,
        Context.MODE_PRIVATE
    )
    private val _stateSharedPreferences = MutableStateFlow(getCurrentLanguage())
    val stateSharedPreferences: StateFlow<Int> = _stateSharedPreferences

    fun setCurrentLanguage(keyLang: Int) {
        sharedPreferences.edit {
            putInt(PREF_CURRENT_LANG_KEY, keyLang)
        }
        _stateSharedPreferences.value = keyLang
    }

    private fun getCurrentLanguage(): Int = sharedPreferences.getInt(PREF_CURRENT_LANG_KEY, START_KEY)

    companion object {
        private const val PREFERENCE_NAME = "krok_pref"
        private const val PREF_CURRENT_LANG_KEY = "current_language"
        const val START_KEY = 3
    }
}