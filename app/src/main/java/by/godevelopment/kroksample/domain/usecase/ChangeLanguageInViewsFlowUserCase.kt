package by.godevelopment.kroksample.domain.usecase

import android.util.Log
import by.godevelopment.kroksample.common.TAG
import by.godevelopment.kroksample.data.datasources.network.entity.KrokView
import by.godevelopment.kroksample.data.datasources.preferences.KrokPreferences
import by.godevelopment.kroksample.data.repositories.NetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class ChangeLanguageInViewsFlowUserCase @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val krokPreferences: KrokPreferences
) {
    operator fun invoke(): Flow<List<KrokView>> = combine(
        networkRepository.getAllViews(),
        krokPreferences.stateSharedPreferences
    ) { list: List<KrokView>, langId ->
        Log.i(TAG, "ChangeLanguageInViewsFlowUserCase: list - ${list.size} lang = $langId")
        val result = list.filter {
            it.lang == langId
        }
        Log.i(TAG, "ChangeLanguageInViewsFlowUserCase: FILTER list - ${result.size} lang = $langId")
        result
    }
}