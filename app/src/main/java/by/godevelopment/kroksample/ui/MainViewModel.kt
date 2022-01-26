package by.godevelopment.kroksample.ui

import androidx.lifecycle.ViewModel
import by.godevelopment.kroksample.data.datasources.preferences.KrokPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val preferences: KrokPreferences
) : ViewModel() {
    fun setLangPreference(key: Int) {
        preferences.setCurrentLanguage(key)
    }
}