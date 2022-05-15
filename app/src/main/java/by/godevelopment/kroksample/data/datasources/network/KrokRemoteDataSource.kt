package by.godevelopment.kroksample.data.datasources.network

import by.godevelopment.kroksample.common.InternetException
import by.godevelopment.kroksample.data.datasources.network.entity.KrokCity
import by.godevelopment.kroksample.data.datasources.network.entity.KrokView
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class KrokRemoteDataSource @Inject constructor(
    private val krokApi: KrokApi,
    private val ioDispatcher: CoroutineDispatcher
) {
    val getAllCities: Flow<List<KrokCity>> = flow {
        val response = krokApi.getAllCities()
        if (response.isSuccessful && response.body() != null) {
            emit(response.body()!!)
        } else throw InternetException()
    }.flowOn(ioDispatcher)

    val getAllViews: Flow<List<KrokView>> = flow {
        val response = krokApi.getAllViews()
        if (response.isSuccessful && response.body() != null) {
            emit(response.body()!!)
        } else throw InternetException()
    }.flowOn(ioDispatcher)
}
