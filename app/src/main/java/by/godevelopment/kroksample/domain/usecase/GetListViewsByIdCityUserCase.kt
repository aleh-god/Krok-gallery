package by.godevelopment.kroksample.domain.usecase

import by.godevelopment.kroksample.data.repositories.NetworkRepository
import by.godevelopment.kroksample.domain.model.ListItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetListViewsByIdCityUserCase @Inject constructor(
    private val networkRepository: NetworkRepository
) {
    operator fun invoke(key: Int, onClick: (Int) -> Unit): Flow<List<ListItemModel>> {
        return networkRepository.getAllViews()
            .map { list ->
                list.filter {
                    it.city_id == key
                }.map { view ->
                    ListItemModel(
                        view.id ?: -1,
                        view.logo ?: "",
                        view.name ?: "null",
                        onClick
                    )
                }
            }
    }
}