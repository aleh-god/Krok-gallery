package by.godevelopment.kroksample.domain.helpers

import android.media.AudioAttributes
import android.media.MediaPlayer
import by.godevelopment.kroksample.domain.model.MediaState
import javax.inject.Inject

class MediaHelper @Inject constructor(
) {
    var mediaState = MediaState.NULL
        private set
    private var mediaPlayer: MediaPlayer? = null
    private var currentTrackUri: String? = null

    fun setMediaPlayer(trackUri: String?) {
        trackUri?.let {
            currentTrackUri = it
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setDataSource(currentTrackUri)
                prepare()
            }
            mediaState = MediaState.STOP
        }
    }

    fun startMusic() {
        currentTrackUri?.let {
            if (mediaState != MediaState.PAUSE) setMediaPlayer(currentTrackUri)
            mediaPlayer?.let {
                it.start()
                mediaState = MediaState.PLAY
            }
        }
    }

    fun pauseMusic() {
        mediaPlayer?.let {
            it.pause()
            mediaState = MediaState.PAUSE
        }
    }

    fun stopMusic() {
        if (mediaState != MediaState.STOP) {
            mediaPlayer?.run {
                stop()
                release()
            }
            mediaState = MediaState.STOP
        }
    }

    fun getCurrentPositionMedia(): Int {
        return mediaPlayer?.currentPosition ?: 0
    }

    fun getDurationMedia(): Int {
        return mediaPlayer?.duration ?: 0
    }
}
