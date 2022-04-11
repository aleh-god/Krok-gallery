package by.godevelopment.kroksample.ui.listregions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kroksample.common.EMPTY_STRING_VALUE
import by.godevelopment.kroksample.data.datasources.krok.KrokData
import by.godevelopment.kroksample.data.datasources.preferences.KrokPreferences
import by.godevelopment.kroksample.domain.usecase.GetRegionListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ListRegionsViewModel @Inject constructor(
    private val getRegionListUseCase: GetRegionListUseCase,
    krokPreferences: KrokPreferences
) : ViewModel() {

    val header = krokPreferences.stateSharedPreferences
        .map { KrokData.header[it] ?: EMPTY_STRING_VALUE }
        .stateIn(viewModelScope, SharingStarted.Lazily, EMPTY_STRING_VALUE)

    var onClickNav: MutableStateFlow<(Int) -> Unit> = MutableStateFlow { }

    val out = onClickNav
        .flatMapLatest { getRegionListUseCase(it) }
        .shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)
}
