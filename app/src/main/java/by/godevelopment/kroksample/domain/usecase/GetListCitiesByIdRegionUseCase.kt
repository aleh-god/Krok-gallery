package by.godevelopment.kroksample.domain.usecase

import by.godevelopment.kroksample.data.repositories.NetworkRepository
import by.godevelopment.kroksample.domain.helpers.StringHelper
import by.godevelopment.kroksample.domain.model.ListItemModel
import by.godevelopment.kroksample.domain.model.Region
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetListCitiesByIdRegionUseCase @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val stringHelper: StringHelper
) {
    operator fun invoke(params: Int, onClick: (Int) -> Unit): Flow<List<ListItemModel>> =
        networkRepository.getAllCities()
            .map { list ->
                var result = emptyList<ListItemModel>()

                Region.getRegionNameById(params)?.let { reg ->
                    val region = stringHelper.getString(reg.text)
                    result = list.filter {
                        it.region == region
                    }.map { city ->
                        ListItemModel(
                            city.id ?: -1,
                            city.logo ?: "",
                            city.name ?: "null",
                            onClick
                        )
                    }
                }
                result
            }
}
