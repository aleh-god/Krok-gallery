package by.godevelopment.kroksample.domain.usecase

import android.util.Log
import by.godevelopment.kroksample.common.EMPTY_STRING_VALUE
import by.godevelopment.kroksample.common.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCityNameByIdCityUserCase @Inject constructor(
    private val changeLanguageInCitiesFlowUserCase: ChangeLanguageInCitiesFlowUserCase
) {
    operator fun invoke(params: Int): Flow<String> =
        changeLanguageInCitiesFlowUserCase()
            .map { list ->
                Log.i(TAG, "GetCityNameByIdCityUserCase invoke: list = ${list.size}")
                list.first {
                    it.id == params
                }.name ?: EMPTY_STRING_VALUE
            }
}