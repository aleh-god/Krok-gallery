package by.godevelopment.kroksample.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kroksample.common.EMPTY_STRING_LINK
import by.godevelopment.kroksample.common.EMPTY_STRING_VALUE
import by.godevelopment.kroksample.common.TAG
import by.godevelopment.kroksample.domain.usecase.GetViewConvertToModelUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getViewConvertToModelUseCase: GetViewConvertToModelUseCase
) : ViewModel() {
    // input flow
    val navArgs = MutableStateFlow(-1)

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
    val showPictures = MutableStateFlow(true)
    val showProgressBar = MutableStateFlow(false)
    val showError = MutableStateFlow(EMPTY_STRING_LINK)
    val playMedia  = MutableStateFlow(EMPTY_STRING_LINK)

    init {
        viewModelScope.launch {
            Log.e(TAG, "DetailsViewModel: init")
            navArgs.flatMapLatest {
                getViewConvertToModelUseCase(it)
            }
                    .onStart {
                        showProgressBar.value = true
                    }
                    .onEach {
                        val data = it
                        header.value = data.name ?: "Null"
                        headerText.value = data.name ?: "Null"
                        text.value = data.text ?: "Null"
                    }
                    .onCompletion {
                        showProgressBar.value = false
                    }
                    .catch {
                        header.value = "Error!"
                        headerText.value = "Loading data failed!"
                    }.collect()
        }
    }
}
