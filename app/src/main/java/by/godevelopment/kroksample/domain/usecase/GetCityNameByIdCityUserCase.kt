package by.godevelopment.kroksample.domain.usecase

import android.util.Log
import by.godevelopment.kroksample.common.EMPTY_STRING_VALUE
import by.godevelopment.kroksample.common.TAG
import by.godevelopment.kroksample.data.repositories.NetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCityNameByIdCityUserCase @Inject constructor(
    private val networkRepository: NetworkRepository
) {
    operator fun invoke(params: Int): Flow<String> =
        networkRepository.getAllCities()
            .map { list ->
                Log.i(TAG, "GetCityNameByIdCityUserCase invoke: ${list.size}")
                list.first {
                    it.id == params
                }.name ?: EMPTY_STRING_VALUE
            }
}