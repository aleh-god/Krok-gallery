package by.godevelopment.kroksample.data.repositories

import android.util.Log
import by.godevelopment.kroksample.common.InternetException
import by.godevelopment.kroksample.common.TAG
import by.godevelopment.kroksample.data.datasources.network.KrokRemoteDataSource
import by.godevelopment.kroksample.data.datasources.network.entity.KrokCity
import by.godevelopment.kroksample.data.datasources.network.entity.KrokView
import by.godevelopment.kroksample.domain.model.CompletedElement
import by.godevelopment.kroksample.domain.model.Element
import by.godevelopment.kroksample.domain.model.ErrorElement
import by.godevelopment.kroksample.domain.model.ItemElement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val krokRemoteDataSource: KrokRemoteDataSource,
    private val externalScope: CoroutineScope
)
{
    fun getAllCities(): Flow<List<KrokCity>> = krokRemoteDataSource.getAllCities
        .map<List<KrokCity>, Element<List<KrokCity>>> {
            ItemElement(it)
        }
        .onCompletion {
            emit(CompletedElement())
        }
        .catch { exception ->
            Log.i(TAG, "NetworkRepository getAllCities.catch ${exception.message}")
            emit(ErrorElement(exception))
        }
        .shareIn(
            externalScope,
            replay = 1,
            started = SharingStarted.WhileSubscribed()
        )
        .map {
            if (it is ErrorElement) throw InternetException()
            return@map it
        }
        .takeWhile { it is ItemElement }
        .map {
            (it as ItemElement).item }

    fun getAllViews(): Flow<List<KrokView>> = krokRemoteDataSource.getAllViews
        .map<List<KrokView>, Element<List<KrokView>>> {
            ItemElement(it)
        }
        .onCompletion {
            emit(CompletedElement())
        }
        .catch { exception ->
            Log.i(TAG, "NetworkRepository getAllViews.catch ${exception.message}")
            emit(ErrorElement(exception))
        }
        .shareIn(
            externalScope,
            replay = 1,
            started = SharingStarted.WhileSubscribed()
        )
        .map {
            if (it is ErrorElement) throw InternetException()
            return@map it
        }
        .takeWhile { it is ItemElement }
        .map {
            (it as ItemElement).item }
}
