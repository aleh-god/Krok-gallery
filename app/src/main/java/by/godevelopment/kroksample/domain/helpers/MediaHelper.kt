package by.godevelopment.kroksample.domain.helpers

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import by.godevelopment.kroksample.common.TAG
import by.godevelopment.kroksample.domain.model.MediaState
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MediaHelper @Inject constructor(
) {
    var mediaState = MediaState.NULL
        private set
    private var mediaPlayer: MediaPlayer? = null
    private var currentTrackUri: String? = null

    fun setMediaPlayer(trackUri: String?) {
        Log.i(TAG, "setMediaPlayer: $trackUri")
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
        Log.i(TAG, "mediaPlayerHolder: startMusic() = $currentTrackUri")
        currentTrackUri?.let {
            Log.i(TAG, "mediaPlayerHolder: startMusic() mediaState = $mediaState")
            if (mediaState != MediaState.PAUSE) setMediaPlayer(currentTrackUri)
            mediaPlayer?.let {
                Log.i(TAG, "mediaPlayerHolder: startMusic() it.start()")
                it.start()
                mediaState = MediaState.PLAY
            }
        }
    }

    fun pauseMusic() {
        Log.i(TAG, "mediaPlayerHolder: pauseMusic()")
        mediaPlayer?.let {
            it.pause()
            mediaState = MediaState.PAUSE
        }
    }

    fun stopMusic() {
        if (mediaState != MediaState.STOP) {
            Log.i(TAG, "mediaPlayerHolder: stopMusic()")
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
        Log.i(TAG, "MusicService: getMaxSecondsMedia()")
        return mediaPlayer?.duration ?: 0
    }

    companion object {
        const val SECOND = 1000
    }
}
