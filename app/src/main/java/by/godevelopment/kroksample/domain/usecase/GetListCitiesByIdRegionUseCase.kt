package by.godevelopment.kroksample.domain.usecase

import by.godevelopment.kroksample.common.EMPTY_INT_VALUE
import by.godevelopment.kroksample.common.EMPTY_STRING_VALUE
import by.godevelopment.kroksample.data.datasources.krok.KrokData
import by.godevelopment.kroksample.domain.model.ListItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetListCitiesByIdRegionUseCase @Inject constructor(
    private val changeLanguageInCitiesFlowUserCase: ChangeLanguageInCitiesFlowUserCase
) {
    operator fun invoke(param: Int, onClick: (Int) -> Unit): Flow<List<ListItemModel>> =
        changeLanguageInCitiesFlowUserCase()
            .map { list ->
                val region = KrokData.regionStringKey[param] ?: EMPTY_STRING_VALUE
                list
                    .filter { it.region == region }
                    .filter {
                        it.name != EMPTY_STRING_VALUE
                    }
                    .map { city ->
                        ListItemModel(
                            city.id ?: EMPTY_INT_VALUE,
                            city.logo ?: EMPTY_STRING_VALUE,
                            city.name ?: EMPTY_STRING_VALUE,
                            onClick
                        )
                    }
            }
}
