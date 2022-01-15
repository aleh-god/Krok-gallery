package by.godevelopment.kroksample.data.repositories

import android.util.Log
import by.godevelopment.kroksample.common.TAG
import by.godevelopment.kroksample.data.datasources.network.KrokRemoteDataSource
import by.godevelopment.kroksample.data.datasources.network.entity.KrokCity
import by.godevelopment.kroksample.data.datasources.network.entity.KrokView
import by.godevelopment.kroksample.data.datasources.preferences.KrokPreferences
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val krokRemoteDataSource: KrokRemoteDataSource,
    private val krokPreferences: KrokPreferences
    ) {
    fun getAllCities(): Flow<List<KrokCity>> = combine(
        krokRemoteDataSource.getAllCities,
        krokPreferences.stateSharedPreferences
    ) { list: List<KrokCity>, langId ->
        Log.i(TAG, "NetworkRepository getAllCities: list - ${list.size} lang = $langId")
        list.filter {
            it.lang == langId
        }
    }

    fun getAllViews(): Flow<List<KrokView>> = combine(
        krokRemoteDataSource.getAllViews,
        krokPreferences.stateSharedPreferences
    ) { list: List<KrokView>, langId ->
                Log.i(TAG, "NetworkRepository getAllViews: list - ${list.size} lang = $langId")
                list.filter {
                    it.lang == langId
                }
            }
}
