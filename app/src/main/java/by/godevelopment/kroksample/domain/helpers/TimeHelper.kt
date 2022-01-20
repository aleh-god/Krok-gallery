package by.godevelopment.kroksample.domain.helpers

import by.godevelopment.kroksample.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TimeHelper @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val tickIntervalMs: Long = 1000
) {
    fun tickerFlow() = flow {
        while (true) {
            emit(Unit)
            delay(tickIntervalMs)
        }
    }.flowOn(defaultDispatcher)
}
