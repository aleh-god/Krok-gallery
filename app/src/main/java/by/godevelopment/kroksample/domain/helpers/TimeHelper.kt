package by.godevelopment.kroksample.domain.helpers

import by.godevelopment.kroksample.common.TICK_INTERVAL_MS
import by.godevelopment.kroksample.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TimeHelper @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {
    fun tickerFlow() = flow {
        while (true) {
            emit(Unit)
            delay(TICK_INTERVAL_MS)
        }
    }.flowOn(defaultDispatcher)
}
