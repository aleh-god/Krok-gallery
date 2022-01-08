package by.godevelopment.kroksample.domain.helpers

import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class TimeHelper: CoroutineScope {

    private var currentTime: Int = 0
    private var startedAtTimestamp: Int = 0
        set(value) {
            currentTime = value
            field = value
        }

    private val handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable = object : Runnable {
        override fun run() {
            currentTime++
            timeUpdate()
            // Repeat every 1 second
            handler.postDelayed(this, 1000)
        }
    }

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    private fun startCoroutineTimer() {
        launch(coroutineContext) {
            handler.post(runnable)
        }
    }

    private fun timeUpdate() {
            // count elapsed time
            val elapsedTime = (currentTime - startedAtTimestamp)
            // send time to update UI
    }

    fun onStop() {
        handler.removeCallbacks(runnable)
        job.cancel()
    }
}