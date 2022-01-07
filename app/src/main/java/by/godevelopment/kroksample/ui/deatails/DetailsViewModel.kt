package by.godevelopment.kroksample.ui.deatails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kroksample.common.EMPTY_STRING_LINK
import by.godevelopment.kroksample.common.EMPTY_STRING_VALUE
import by.godevelopment.kroksample.common.TAG
import by.godevelopment.kroksample.domain.model.Result
import by.godevelopment.kroksample.domain.usecase.GetViewConvertToModelUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getViewConvertToModelUseCase: GetViewConvertToModelUseCase
) : ViewModel() {
    // model flow
    val header = MutableStateFlow(EMPTY_STRING_VALUE)
    val headerText = MutableStateFlow(EMPTY_STRING_VALUE)
    val text = MutableStateFlow(EMPTY_STRING_VALUE)
    val pictures = MutableStateFlow(EMPTY_STRING_VALUE)

    // media flow
    val currentTimeText = MutableStateFlow(EMPTY_STRING_VALUE)
    val totalTimeText = MutableStateFlow(EMPTY_STRING_VALUE)

    val mediaIsPlayed = MutableStateFlow(false)
    val mediaIsPaused = MutableStateFlow(false)
    val dataIsNotReady = MutableStateFlow(true)

    // event flow
    val showPictures = MutableStateFlow(EMPTY_STRING_LINK)
    val showError = MutableStateFlow(EMPTY_STRING_LINK)
    val playMedia  = MutableStateFlow(EMPTY_STRING_LINK)

    init {
        viewModelScope.launch {
            Log.e(TAG, "DetailsViewModel: init")
            val temp = getViewConvertToModelUseCase(9)
            temp.collect {
                when(it) {
                    is Result.Success -> {
                        val data = it.data
                        header.value = data.header
                        headerText.value = data.headerText
                        text.value = data.text
                        showPictures.value = data.pictures
                        playMedia.value = data.sound
                    }
                    is Result.Error -> {
                        val message = it.message
                        showError.value = message
                    }
                }
            }
        }
    }
}
