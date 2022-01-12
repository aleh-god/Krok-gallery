package by.godevelopment.kroksample.data.datasources.network

import android.util.Log
import by.godevelopment.kroksample.common.InternetException
import by.godevelopment.kroksample.common.TAG
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
        Log.i(TAG, "KrokRemoteDataSource - getAllCities: start")
        val response = krokApi.getAllCities()
        if (response.isSuccessful && response.body() != null) {
            emit(response.body()!!)
        } else throw InternetException()
    }.flowOn(ioDispatcher)

    val getAllViews: Flow<List<KrokView>> = flow {
        Log.i(TAG, "KrokRemoteDataSource - getAllCities: start")
        val response = krokApi.getAllViews()
        if (response.isSuccessful && response.body() != null) {
            emit(response.body()!!)
        } else throw InternetException()
    }.flowOn(ioDispatcher)
}

//    suspend fun getKrokPointList(krokPlace: Int): Result<List<KrokPoint>>
//    = withContext(ioDispatcher) {
//        Log.e(TAG, "getKrokPointList: start")
//        try {
//            val response = krokApi.getKrokPointList(krokPlace)
//            if (response.isSuccessful && response.body() != null) {
//                val result = response.body()!!
//                return@withContext Result.Success<List<KrokPoint>>(result)
//            } else return@withContext Result.Error("Result is null")
//        } catch (e: IOException) {
//            Log.e(TAG, "IOException, you might not have internet connection")
//            return@withContext Result.Error("IOException, you might not have internet connection")
//        } catch (e: HttpException) {
//            Log.e(TAG, "HttpException, unexpected response")
//            return@withContext Result.Error("HttpException, unexpected response")
//        } catch (e: Exception) {
//            Log.e(TAG, "AnonException, what happens wrong")
//            return@withContext Result.Error("AnonException, what happens wrong")
//        }
//    }