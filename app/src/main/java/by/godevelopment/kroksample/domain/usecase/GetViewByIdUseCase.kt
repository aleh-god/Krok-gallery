package by.godevelopment.kroksample.domain.usecase

import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetViewByIdUseCase @Inject constructor(
    private val changeLanguageInViewsFlowUserCase: ChangeLanguageInViewsFlowUserCase
) {
    operator fun invoke(id: Int) = changeLanguageInViewsFlowUserCase()
        .map { list ->
            list.first { krok ->
                krok.id_point == id
            }
        }
}
