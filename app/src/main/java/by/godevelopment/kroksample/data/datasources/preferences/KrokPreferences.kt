package by.godevelopment.kroksample.data.datasources.preferences

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.viewModelScope
import by.godevelopment.kroksample.common.EMPTY_STRING_VALUE
import by.godevelopment.kroksample.common.TAG
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class KrokPreferences @Inject constructor(
    @ApplicationContext appContext: Context,
    private val coroutineScope: CoroutineScope
) {
    private val sharedPreferences = appContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    private val _stateSharedPreferences = MutableStateFlow(START_KEY)
    val stateSharedPreferences: StateFlow<Int> = _stateSharedPreferences

    init {
        coroutineScope.launch {
            stateSharedPreferences.collect {
                Log.i(TAG, "KrokPreferences.collect : $it")
            }
        }
    }

    fun setCurrentLanguage(keyLang: Int) {
        sharedPreferences.edit {
            Log.i(TAG, "setCurrentLanguage: $keyLang")
            putInt(PREF_CURRENT_LANG_KEY, keyLang)
        }
        _stateSharedPreferences.value = keyLang
    }

    fun getCurrentLanguage(): Int = sharedPreferences.getInt(PREF_CURRENT_LANG_KEY, START_KEY)

    companion object {
        private const val PREFERENCE_NAME = "krok_pref"
        private const val PREF_CURRENT_LANG_KEY = "current_language"
        const val START_KEY = 3
    }
}