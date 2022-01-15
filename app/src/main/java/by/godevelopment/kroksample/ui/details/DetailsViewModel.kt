package by.godevelopment.kroksample.ui.details

import android.os.Build
import android.text.Html
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kroksample.common.EMPTY_STRING_LINK
import by.godevelopment.kroksample.common.EMPTY_STRING_TIME
import by.godevelopment.kroksample.common.EMPTY_STRING_VALUE
import by.godevelopment.kroksample.common.TAG
import by.godevelopment.kroksample.domain.helpers.MediaHelper
import by.godevelopment.kroksample.domain.model.MediaState
import by.godevelopment.kroksample.domain.usecase.GetViewConvertToModelUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getViewConvertToModelUseCase: GetViewConvertToModelUseCase,
    private val mediaHelper: MediaHelper
) : ViewModel() {
    // input flow
    val navArgs = MutableStateFlow(-1)

    // model flow
    val uiState = MutableStateFlow(UiStateModel())
    val currentTimeText = EMPTY_STRING_TIME
    val totalTimeText = EMPTY_STRING_TIME

    // media flow

    // event flow
    val showProgressBar = MutableStateFlow(false)
    val showPictures = uiState.map {
        !it.imageView.isNullOrBlank()
    }.asStateFlow(false)
    val showMediaPlayer = uiState.map {
        !it.mediaLink.isNullOrBlank()
    }.asStateFlow(false)

    val showError = MutableStateFlow(EMPTY_STRING_LINK)

    init {
        viewModelScope.launch {
            Log.i(TAG, "DetailsViewModel: init")
            navArgs.flatMapLatest {
                getViewConvertToModelUseCase(it)
            }
                    .onStart {
                        Log.i(TAG, "DetailsViewModel: .onStart")
                        showProgressBar.value = true
                    }
                    .onEach { krok ->
                        Log.i(TAG, "DetailsViewModel: .onEach $krok")
                        uiState.value = UiStateModel(
                        header = krok.name ?: "No information",
                        headerText = krok.name ?: "No information",
                        text = krok.text ?: "No information",
                        imageView = krok.photo,
                        mediaLink = krok.sound
                        )
                        showProgressBar.value = false
                    }
                    .onCompletion {
                        Log.i(TAG, "DetailsViewModel: .onCompletion")
                        showProgressBar.value = false
                    }
                    .catch {
                        Log.i(TAG, "DetailsViewModel: .catch")
                        uiState.value = UiStateModel(
                        header = "Error!",
                        headerText = "Loading data failed!"
                        )
                    }.collect()
        }

        viewModelScope.launch {
            showMediaPlayer.collect {
                if (it) {
                    mediaHelper.setMediaPlayer(uiState.value.mediaLink)
                }
            }
        }
    }

    fun onClickPlayPause() {
        mediaHelper.startMusic()
    }

    fun onClickStop() {
        mediaHelper.stopMusic()
    }

    fun htmlToString(input: String) {
        val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(input, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(input)
        }
    }

    private fun <T> Flow<T>.asStateFlow(init: T) =
        stateIn(viewModelScope, SharingStarted.Lazily, init)

    data class UiStateModel(
        val header: String = EMPTY_STRING_VALUE,
        val headerText: String = EMPTY_STRING_VALUE,
        val text: String = EMPTY_STRING_VALUE,
        val imageView: String? = null,
        val mediaLink: String? = null
    )

    sealed class MediaPlayerState() {
        data class Online(val mediaState: MediaState): MediaPlayerState()
        object OffLine: MediaPlayerState()
        object Error: MediaPlayerState()
        object Loading: MediaPlayerState()
    }
}
