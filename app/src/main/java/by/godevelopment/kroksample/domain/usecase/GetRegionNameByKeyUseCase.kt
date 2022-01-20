package by.godevelopment.kroksample.domain.usecase

import by.godevelopment.kroksample.common.EMPTY_STRING_VALUE
import by.godevelopment.kroksample.data.datasources.krok.KrokData
import by.godevelopment.kroksample.data.datasources.preferences.KrokPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRegionNameByKeyUseCase @Inject constructor(
    private val krokPreferences: KrokPreferences
) {
    operator fun invoke(params: Int): Flow<String> {
        return krokPreferences.stateSharedPreferences.map { idLang ->
            KrokData.regionRU.first {
                it.id == params
            }.text[idLang] ?: EMPTY_STRING_VALUE
        }
    }
}