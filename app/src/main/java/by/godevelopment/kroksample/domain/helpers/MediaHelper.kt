package by.godevelopment.kroksample.domain.helpers

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import by.godevelopment.kroksample.common.TAG
import by.godevelopment.kroksample.domain.model.MediaItem
import by.godevelopment.kroksample.domain.model.MediaState
import javax.inject.Inject

class MediaHelper @Inject constructor(
    private val context: Context
    ) {

    private var mediaState = MediaState.NULL
    private var mediaPlayer: MediaPlayer? = null

    private fun setMediaPlayer(mediaItem: MediaItem) {
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(mediaItem.trackUri)
            prepare()
        }
        mediaState = MediaState.STOP
    }

    fun startMusic() {
        Log.i(TAG, "mediaPlayerHolder: startMusic() = $mediaState")
        // if (mediaState != MediaState.PAUSE) setMediaPlayer(getCurrentMusicItem())
        mediaPlayer?.start()
        mediaState = MediaState.PLAY
    }

    fun pauseMusic() {
        Log.i(TAG, "mediaPlayerHolder: pauseMusic()")
        mediaPlayer?.pause()
        mediaState = MediaState.PAUSE
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

    fun getCurrentSecondsMedia(): Int {
        return mediaPlayer?.currentPosition ?: 0
    }

    fun getMaxSecondsMedia(): Int {
        Log.i(TAG, "MusicService: getMaxSecondsMedia()")
        return mediaPlayer?.duration ?: 0
    }

    fun setProgressPlayingMedia(progress: Int) {
        Log.i(TAG, "MusicService: setProgressPlayingMedia()")
        mediaPlayer?.seekTo(progress * SECOND)
    }

    companion object {
        const val SECOND = 1000
    }
}
