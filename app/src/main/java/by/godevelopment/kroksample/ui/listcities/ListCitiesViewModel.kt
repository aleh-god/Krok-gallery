package by.godevelopment.kroksample.ui.listcities

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kroksample.R
import by.godevelopment.kroksample.common.*
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
    val idKey = MutableStateFlow(EMPTY_INT_VALUE)
    var onClickNav: (Int) -> Unit = { key ->
        startNavigation(key)
    }

    private val _navigationEvent: MutableLiveData<Event<Int>> = MutableLiveData(Event(null))
    val navigationEvent: LiveData<Event<Int>> = _navigationEvent

    private fun startNavigation(key: Int) {
        _navigationEvent.value = Event(key)
    }

    // out  flow
    val out = idKey.flatMapLatest { key ->
        Log.i(TAG, "ListCitiesViewModel out: key = $key")
        getListCitiesByIdRegionUseCase(key, onClickNav) }
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
            if (it is ErrorElement) throw ApplicationException()
            return@map it
        }
        .takeWhile { it is ItemElement }
        .map {
            Log.i(TAG, "ListCitiesViewModel out: .map $it")
            (it as ItemElement).item }

    val header = idKey.flatMapLatest {
        Log.i(TAG, "ListViewModel: header = $it")
        getRegionNameByKeyUseCase(it)
    }.catch {
        Log.i(TAG, "ListViewModel: catch header")
        emit(stringHelper.getString(R.string.message_error_loading))
    }.asStateFlow(EMPTY_STRING_VALUE)

    private fun <T> Flow<T>.asStateFlow(init: T) =
        stateIn(viewModelScope, SharingStarted.Lazily, init)
}
