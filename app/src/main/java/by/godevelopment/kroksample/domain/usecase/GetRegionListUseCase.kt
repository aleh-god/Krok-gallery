package by.godevelopment.kroksample.domain.usecase

import android.util.Log
import by.godevelopment.kroksample.common.TAG
import by.godevelopment.kroksample.data.datasources.krok.KrokData
import by.godevelopment.kroksample.domain.model.RegionItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRegionListUseCase @Inject constructor(

){
    fun execute(): Flow<List<RegionItem>> = flow {
        Log.i(TAG, "execute: GetRegionListUseCase start")
        emit(KrokData.regionRU)
    }
}