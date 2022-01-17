package by.godevelopment.kroksample.ui.listcities

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kroksample.R
import by.godevelopment.kroksample.common.EMPTY_STRING_VALUE
import by.godevelopment.kroksample.common.InternetException
import by.godevelopment.kroksample.common.TAG
import by.godevelopment.kroksample.domain.helpers.StringHelper
import by.godevelopment.kroksample.domain.model.*
import by.godevelopment.kroksample.domain.usecase.GetListCitiesByIdRegionUseCase
import by.godevelopment.kroksample.domain.usecase.GetRegionNameByKeyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ListCitiesViewModel @Inject constructor(
    private val getListCitiesByIdRegionUseCase: GetListCitiesByIdRegionUseCase,
    private val getRegionNameByKeyUseCase: GetRegionNameByKeyUseCase,
    private val stringHelper: StringHelper
) : ViewModel() {
    // input flow
    var onClickNav: MutableStateFlow<(Int) -> Unit> = MutableStateFlow { }
    val idKey = MutableStateFlow(-1)

    // out  flow
    val out = idKey.flatMapLatest { key ->
        Log.i(TAG, "ListCitiesViewModel out: key = $key")
        getListCitiesByIdRegionUseCase(key, onClickNav.value) }
        .onStart {
            Log.i(TAG, "ListCitiesViewModel out: .onStart")
        }
        .map<List<ListItemModel>, Element<List<ListItemModel>>> { item ->
            Log.i(TAG, "ListCitiesViewModel out: .map = ${item.size}")
            ItemElement(item) }
        .onCompletion {
            Log.i(TAG, "ListCitiesViewModel out: .onCompletion")
            emit(CompletedElement())
        }
        .catch { exception ->
            Log.i(TAG, "ListCitiesViewModel out: .catch ${exception.message}")
            emit(ErrorElement(exception))
        }
        .shareIn(viewModelScope, SharingStarted.Eagerly, 1)
        .map {
            Log.i(TAG, "ListCitiesViewModel out: .map-2 ${it}")
            if (it is ErrorElement) throw it.error
            return@map it
        }
        .takeWhile { it is ItemElement }
        .map {
            Log.i(TAG, "ListCitiesViewModel out: .map-3 ${it}")
            (it as ItemElement).item }

    // layout flow
    val header = idKey.flatMapLatest {
        Log.i(TAG, "ListViewModel: header = $it")
        getRegionNameByKeyUseCase.execute(it)
    }.catch {
        Log.i(TAG, "ListViewModel: catch header")
        emit(stringHelper.getString(R.string.error_loading))
    }.asStateFlow(EMPTY_STRING_VALUE)


    private fun <T> Flow<T>.asStateFlow(init: T) =
        stateIn(viewModelScope, SharingStarted.Lazily, init)

    private fun <T> Flow<T>.asLiveDataFlow() =
        shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)
}
