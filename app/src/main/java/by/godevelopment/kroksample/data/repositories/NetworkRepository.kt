package by.godevelopment.kroksample.data.repositories

import android.util.Log
import by.godevelopment.kroksample.common.TAG
import by.godevelopment.kroksample.data.datasources.network.KrokApi
import by.godevelopment.kroksample.data.datasources.network.KrokRemoteDataSource
import by.godevelopment.kroksample.data.datasources.network.entity.KrokCity
import by.godevelopment.kroksample.data.datasources.network.entity.KrokPoint
import by.godevelopment.kroksample.data.datasources.network.entity.KrokView
import by.godevelopment.kroksample.domain.model.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val krokRemoteDataSource: KrokRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher
    ) {

    fun getAllCities(): Flow<List<KrokCity>> =
        krokRemoteDataSource.getAllCities

    fun getAllViews(): Flow<List<KrokView>> =
        krokRemoteDataSource.getAllViews

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
//
//    suspend fun getView(id: Int): Result<KrokView>
//            = withContext(ioDispatcher) {
//        Log.e(TAG, "getView: start")
//        try {
//            val response = krokApi.getKrokView(id)
//            if (response.isSuccessful && response.body() != null) {
//                val result = response.body()!!
//                return@withContext Result.Success<KrokView>(result)
//            } else return@withContext Result.Error("Result is null")
//        } catch (e: IOException) {
//            Log.e(TAG, "IOException, you might not have internet connection")
//            return@withContext Result.Error("IOException, you might not have internet connection")
//        } catch (e: HttpException) {
//            Log.e(TAG, "HttpException, unexpected response")
//            return@withContext Result.Error("HttpException, unexpected response")
//        } catch (e: Exception) {
//            Log.e(TAG, "AnonException, what happens wrong ${e.message}")
//            return@withContext Result.Error("AnonException, what happens wrong")
//        }
//    }
}
