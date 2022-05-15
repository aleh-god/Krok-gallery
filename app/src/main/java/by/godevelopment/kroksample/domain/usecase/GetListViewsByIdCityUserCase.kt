package by.godevelopment.kroksample.domain.usecase

import by.godevelopment.kroksample.common.EMPTY_INT_VALUE
import by.godevelopment.kroksample.common.EMPTY_STRING_VALUE
import by.godevelopment.kroksample.domain.model.ListItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetListViewsByIdCityUserCase @Inject constructor(
    private val changeLanguageInViewsFlowUserCase: ChangeLanguageInViewsFlowUserCase
) {
    operator fun invoke(param: Int, onClick: (Int) -> Unit): Flow<List<ListItemModel>> {
        return changeLanguageInViewsFlowUserCase()
            .map { list ->
                list
                    .filter {
                        it.city_id == param
                    }
                    .filter {
                        it.name != EMPTY_STRING_VALUE
                    }
                    .map { view ->
                        ListItemModel(
                            view.id_point ?: EMPTY_INT_VALUE,
                            view.logo ?: EMPTY_STRING_VALUE,
                            view.name ?: EMPTY_STRING_VALUE,
                            onClick
                        )
                    }
            }
    }
}