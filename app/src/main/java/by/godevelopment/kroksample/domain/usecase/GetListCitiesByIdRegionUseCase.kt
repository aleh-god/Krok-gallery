package by.godevelopment.kroksample.domain.usecase

import android.util.Log
import by.godevelopment.kroksample.common.TAG
import by.godevelopment.kroksample.data.repositories.NetworkRepository
import by.godevelopment.kroksample.domain.model.ListItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetListCitiesByIdRegionUseCase @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val getRegionNameByKeyUseCase: GetRegionNameByKeyUseCase
) {
    operator fun invoke(param: Int, onClick: (Int) -> Unit): Flow<List<ListItemModel>> =
        networkRepository.getAllCities()
            .map { list ->
                val region = getRegionNameByKeyUseCase.run(param)
                Log.i(TAG, "GetListCitiesByIdRegionUseCase invoke: $param")
                    list
                        .filter {
                            it.region == region
                        }
                        .map { city ->
                            ListItemModel(
                            city.id ?: -1,
                            city.logo ?: "",
                            city.name ?: "No information",
                            onClick
                        )
                    }
            }
}
