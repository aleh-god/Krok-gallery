package by.godevelopment.kroksample.ui.listcities

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kroksample.common.EMPTY_STRING_VALUE
import by.godevelopment.kroksample.common.TAG
import by.godevelopment.kroksample.data.TestData
import by.godevelopment.kroksample.data.datasources.preferences.KrokPreferences
import by.godevelopment.kroksample.domain.model.ListItemModel
import by.godevelopment.kroksample.domain.model.Region
import by.godevelopment.kroksample.domain.usecase.GetListCitiesByIdRegionUseCase
import by.godevelopment.kroksample.domain.usecase.GetRegionNameByKeyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListCitiesViewModel @Inject constructor(
    private val getListCitiesByIdRegionUseCase: GetListCitiesByIdRegionUseCase,
    private val getRegionNameByKeyUseCase: GetRegionNameByKeyUseCase
) : ViewModel() {
    // input flow
    var onClickNav: MutableStateFlow<(Int) -> Unit> = MutableStateFlow { }
    val idKey = MutableStateFlow(-1)

    // out  flow
    val out = idKey.flatMapLatest { key ->
        Log.i(TAG, "ListCitiesViewModel out: key = $key")
        if (key != -1) header.value = getRegionNameByKeyUseCase(key)
        getListCitiesByIdRegionUseCase(key, onClickNav.value)
    }.asLiveDataFlow()

    // model flow
    val header = MutableStateFlow(EMPTY_STRING_VALUE)

    private fun <T> Flow<T>.asLiveDataFlow() =
        shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)
}