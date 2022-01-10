package by.godevelopment.kroksample.ui

import androidx.lifecycle.ViewModel
import by.godevelopment.kroksample.data.datasources.krok.KrokData
import by.godevelopment.kroksample.data.datasources.preferences.KrokPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val preferences: KrokPreferences
) : ViewModel() {

    // TODO = "delete this"
//    val header = MutableStateFlow(KrokData.header)
//    var onClickNav: MutableStateFlow<(Int) -> Unit> = MutableStateFlow { }

    fun setLangPreference(key: Int) {
        preferences.setCurrentLanguage(key)
    }
}