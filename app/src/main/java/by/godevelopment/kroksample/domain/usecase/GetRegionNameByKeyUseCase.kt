package by.godevelopment.kroksample.domain.usecase

import android.util.Log
import by.godevelopment.kroksample.common.TAG
import by.godevelopment.kroksample.domain.helpers.StringHelper
import by.godevelopment.kroksample.domain.model.Region
import javax.inject.Inject

class GetRegionNameByKeyUseCase @Inject constructor(
    private val stringHelper: StringHelper
) {
    operator fun invoke(params: Int): String {
        return Region.getRegionNameById(params)?.let { reg ->
            Log.i(TAG, "GetRegionNameByKeyUseCase invoke: $reg")
            stringHelper.getString(reg.text)
        } ?: "No information"
    }
}