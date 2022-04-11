package by.godevelopment.kroksample.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kroksample.R
import by.godevelopment.kroksample.common.EMPTY_INT_VALUE
import by.godevelopment.kroksample.common.EMPTY_STRING_VALUE
import by.godevelopment.kroksample.common.TAG
import by.godevelopment.kroksample.domain.helpers.MediaHelper
import by.godevelopment.kroksample.domain.helpers.StringHelper
import by.godevelopment.kroksample.domain.helpers.TimeHelper
import by.godevelopment.kroksample.domain.model.MediaState
import by.godevelopment.kroksample.domain.usecase.GetViewByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getViewByIdUseCase: GetViewByIdUseCase,
    private val mediaHelper: MediaHelper,
    private val timeHelper: TimeHelper,
    private val stringHelper: StringHelper
) : ViewModel() {

    val navArgs = MutableStateFlow(EMPTY_INT_VALUE)

    private var currentStateMedia = MediaPlayerStateModel()

    val uiState = MutableStateFlow(UiStateModel())

    val playerIsOn = MutableStateFlow(false)

    val showProgressBar = MutableStateFlow(false)

    val showPictures = uiState
        .map { !it.imageView.isNullOrBlank() }
        .asStateFlow(false)

    val showMediaPlayer = uiState
        .map { !it.mediaLink.isNullOrBlank() }
        .asStateFlow(false)

    val mediaState = playerIsOn
        .flatMapLatest { if (it) {
            timeHelper.tickerFlow().map {
                MediaPlayerStateModel(
                    mediaHelper.getDurationMedia(),
                    mediaHelper.getCurrentPositionMedia()
                ).also { state ->
                    currentStateMedia = state
                }
            }
        } else flowOf(currentStateMedia)
        }
        .asStateFlow(MediaPlayerStateModel())

    init {
        viewModelScope.launch {
            navArgs
                .flatMapLatest { getViewByIdUseCase(it) }
                .onStart { showProgressBar.value = true }
                .onEach { krok ->
                    uiState.value = UiStateModel(
                        header = krok.name ?: EMPTY_STRING_VALUE,
                        headerText = krok.name ?: EMPTY_STRING_VALUE,
                        text = krok.text ?: EMPTY_STRING_VALUE,
                        imageView = krok.photo,
                        mediaLink = krok.sound)
                    showProgressBar.value = false
                }
                .onCompletion {
                    showProgressBar.value = false
                    mediaHelper.stopMusic()
                    playerIsOn.value = false
                }
                .catch { exception ->
                    Log.i(TAG, "DetailsViewModel: .catch ${exception.message}")
                    uiState.value = UiStateModel(
                        header = stringHelper.getString(R.string.message_error),
                        headerText = stringHelper.getString(R.string.message_error_loading))
                }
                .collect()
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
