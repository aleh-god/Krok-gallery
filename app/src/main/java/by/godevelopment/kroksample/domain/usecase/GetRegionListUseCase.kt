package by.godevelopment.kroksample.domain.usecase

import android.util.Log
import by.godevelopment.kroksample.common.EMPTY_STRING_VALUE
import by.godevelopment.kroksample.common.TAG
import by.godevelopment.kroksample.data.datasources.krok.KrokData
import by.godevelopment.kroksample.data.datasources.preferences.KrokPreferences
import by.godevelopment.kroksample.domain.model.ListItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRegionListUseCase @Inject constructor(
    private val krokPreferences: KrokPreferences
){
    operator fun invoke(onClick: (Int) -> Unit): Flow<List<ListItemModel>>
    = krokPreferences.stateSharedPreferences.map { idLang ->
        Log.i(TAG, "execute: GetRegionListUseCase start idLang = $idLang")
        KrokData.regionRU.map { regionItem ->
            ListItemModel(
                regionItem.id,
                regionItem.pictures,
                regionItem.text[idLang] ?: EMPTY_STRING_VALUE,
                onClick
            )
        }
    }
}