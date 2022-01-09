package by.godevelopment.kroksample.domain.usecase

import android.util.Log
import by.godevelopment.kroksample.common.TAG
import by.godevelopment.kroksample.data.datasources.krok.KrokData
import by.godevelopment.kroksample.domain.model.ListItemModel
import by.godevelopment.kroksample.domain.model.RegionItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRegionListUseCase @Inject constructor(
){
    fun execute(onClick: (Int) -> Unit): Flow<List<ListItemModel>> = flow {
        Log.i(TAG, "execute: GetRegionListUseCase start")
        val result = KrokData.regionRU.map { regionItem ->
            ListItemModel(
                regionItem.id,
                regionItem.pictures,
                regionItem.text,
                onClick
            )
        }
        emit(result)
    }
}