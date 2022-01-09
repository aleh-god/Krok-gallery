package by.godevelopment.kroksample.data.repositories

import by.godevelopment.kroksample.data.datasources.network.KrokRemoteDataSource
import by.godevelopment.kroksample.data.datasources.network.entity.KrokCity
import by.godevelopment.kroksample.data.datasources.network.entity.KrokView
import by.godevelopment.kroksample.data.datasources.preferences.KrokPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val krokRemoteDataSource: KrokRemoteDataSource,
    private val krokPreferences: KrokPreferences,
    private val ioDispatcher: CoroutineDispatcher
    ) {

    fun getAllCities(): Flow<List<KrokCity>> =
        krokRemoteDataSource.getAllCities
            .map { list ->
                val langId = krokPreferences.getCurrentLanguage()
                list.filter {
                    it.lang == langId
                }
            }
            .flowOn(ioDispatcher)

    fun getAllViews(): Flow<List<KrokView>> =
        krokRemoteDataSource.getAllViews
            .map { list ->
                val langId = krokPreferences.getCurrentLanguage()
                list.filter {
                    it.lang == langId
                }
            }
            .flowOn(ioDispatcher)
}

//             .onEach { news -> // Executes on the default dispatcher
//                saveInCache(news)
//            }