package by.godevelopment.kroksample.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kroksample.R
import by.godevelopment.kroksample.common.EMPTY_STRING_LINK
import by.godevelopment.kroksample.common.EMPTY_STRING_VALUE
import by.godevelopment.kroksample.common.TAG
import by.godevelopment.kroksample.domain.helpers.MediaHelper
import by.godevelopment.kroksample.domain.helpers.StringHelper
import by.godevelopment.kroksample.domain.helpers.TimeHelper
import by.godevelopment.kroksample.domain.model.MediaState
import by.godevelopment.kroksample.domain.usecase.GetViewConvertToModelUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getViewConvertToModelUseCase: GetViewConvertToModelUseCase,
    private val mediaHelper: MediaHelper,
    private val timeHelper: TimeHelper,
    private val stringHelper: StringHelper
) : ViewModel() {
    // input flow
    val navArgs = MutableStateFlow(-1)

    // model flow
    private var currentStateMedia = MediaPlayerStateModel()
    val uiState = MutableStateFlow(UiStateModel())
    val playerIsOn = MutableStateFlow(false)
    val showProgressBar = MutableStateFlow(false)
    val showPictures = uiState.map {
        !it.imageView.isNullOrBlank() }.asStateFlow(false)
    val showMediaPlayer = uiState.map {
        !it.mediaLink.isNullOrBlank() }.asStateFlow(false)

    val mediaState = playerIsOn.flatMapLatest {
        Log.i(TAG, "mediaState: $it")
        if (it) {
            timeHelper.tickerFlow().map {
                MediaPlayerStateModel(
                    mediaHelper.getDurationMedia(),
                    mediaHelper.getCurrentPositionMedia()
                ).also { state ->
                    currentStateMedia = state
                    Log.i(TAG, "mediaState: currentStateMedia $currentStateMedia")
                }
            }
        } else flowOf(currentStateMedia)
    }.asStateFlow(
        MediaPlayerStateModel()
    )

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
                        header = krok.name ?: EMPTY_STRING_VALUE,
                        headerText = krok.name ?: EMPTY_STRING_VALUE,
                        text = krok.text ?: EMPTY_STRING_VALUE,
                        imageView = krok.photo,
                        mediaLink = krok.sound
                        )
                        showProgressBar.value = false
                    }
                    .onCompletion {
                        Log.i(TAG, "DetailsViewModel: .onCompletion")
                        showProgressBar.value = false
                        mediaHelper.stopMusic()
                        playerIsOn.value = false
                    }
                    .catch () { exception ->
                        Log.i(TAG, "DetailsViewModel: .catch ${exception.message}")
                        uiState.value = UiStateModel(
                        header = stringHelper.getString(R.string.message_error),
                        headerText = stringHelper.getString(R.string.message_error_loading)
                        )
                    }.collect()  //asStateFlow(UiStateModel())
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
        if (mediaHelper.mediaState != MediaState.PLAY ) mediaHelper.startMusic()
        else mediaHelper.pauseMusic()
        playerIsOn.value = !playerIsOn.value
    }

    fun onClickStop() {
        currentStateMedia = MediaPlayerStateModel()
        Log.i(TAG, "onClickStop(): currentStateMedia $currentStateMedia")
        mediaHelper.stopMusic()
        playerIsOn.value = false
    }

    fun onStopMedia() {
        mediaHelper.stopMusic()
        playerIsOn.value = false
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

    data class MediaPlayerStateModel(
        val DurationMedia: Int = 0,
        val CurrentPositionMedia: Int = 0
    )
}
