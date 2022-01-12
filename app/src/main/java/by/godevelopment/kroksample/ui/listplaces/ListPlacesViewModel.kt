package by.godevelopment.kroksample.ui.listplaces

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kroksample.common.EMPTY_STRING_VALUE
import by.godevelopment.kroksample.common.TAG
import by.godevelopment.kroksample.data.TestData
import by.godevelopment.kroksample.domain.model.ListItemModel
import by.godevelopment.kroksample.domain.usecase.GetCityNameByIdCityUserCase
import by.godevelopment.kroksample.domain.usecase.GetListCitiesByIdRegionUseCase
import by.godevelopment.kroksample.domain.usecase.GetListViewsByIdCityUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ListPlacesViewModel @Inject constructor(
    private val getListViewsByIdCityUserCase: GetListViewsByIdCityUserCase,
    private val getCityNameByIdCityUserCase: GetCityNameByIdCityUserCase
) : ViewModel() {
    // input flow
    var onClickNav: MutableStateFlow<(Int) -> Unit> = MutableStateFlow { }
    val idKey = MutableStateFlow(-1)

    // out flow
    val out = idKey.flatMapLatest { key ->
        Log.i(TAG, "ListCitiesViewModel out: key = $key")
        getListViewsByIdCityUserCase(key, onClickNav.value)
    }.asLiveDataFlow()

    val header = idKey.flatMapLatest { key ->
        getCityNameByIdCityUserCase(key)
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        EMPTY_STRING_VALUE
    )

    private fun <T> Flow<T>.asLiveDataFlow() =
        shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)
}