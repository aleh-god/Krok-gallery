package by.godevelopment.kroksample.domain.usecase

import android.util.Log
import by.godevelopment.kroksample.common.TAG
import by.godevelopment.kroksample.data.datasources.network.entity.KrokCity
import by.godevelopment.kroksample.data.datasources.preferences.KrokPreferences
import by.godevelopment.kroksample.data.repositories.NetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class ChangeLanguageInCitiesFlowUserCase @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val krokPreferences: KrokPreferences
) {
    operator fun invoke(): Flow<List<KrokCity>> = combine(
        networkRepository.getAllCities(),
        krokPreferences.stateSharedPreferences
    ) { list: List<KrokCity>, langId ->
        Log.i(TAG, "ChangeLanguageInCitiesFlowUserCase: list - ${list.size} lang = $langId")
        list.filter {
            it.lang == langId
        }
    }
}