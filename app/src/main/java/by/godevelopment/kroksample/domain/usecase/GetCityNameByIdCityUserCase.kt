package by.godevelopment.kroksample.domain.usecase

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
                list.first {
                    it.id == params
                }.name ?: "Null city"
            }
}