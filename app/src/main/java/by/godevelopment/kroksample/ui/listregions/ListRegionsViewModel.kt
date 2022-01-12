package by.godevelopment.kroksample.ui.listregions

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kroksample.common.TAG
import by.godevelopment.kroksample.data.datasources.krok.KrokData
import by.godevelopment.kroksample.domain.usecase.GetRegionListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ListRegionsViewModel @Inject constructor(
    private val getRegionListUseCase: GetRegionListUseCase
) : ViewModel() {

    val header = MutableStateFlow(KrokData.header)
    var onClickNav: MutableStateFlow<(Int) -> Unit> = MutableStateFlow { }

    val out = onClickNav.flatMapLatest {
        Log.i(TAG, "ListRegionsViewModel: onClickNav.flatMapLatest")
        getRegionListUseCase.execute(it)
    }.asLiveDataFlow()

    private fun <T> Flow<T>.asLiveDataFlow() =
        shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)
}
