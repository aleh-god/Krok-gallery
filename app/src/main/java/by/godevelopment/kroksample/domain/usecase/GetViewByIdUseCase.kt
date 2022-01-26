package by.godevelopment.kroksample.domain.usecase

import android.util.Log
import by.godevelopment.kroksample.common.TAG
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetViewByIdUseCase @Inject constructor(
    private val changeLanguageInViewsFlowUserCase: ChangeLanguageInViewsFlowUserCase
) {
    operator fun invoke(id: Int) = changeLanguageInViewsFlowUserCase()
        .map { list ->
            Log.i(TAG, "GetViewByIdUseCase invoke list = ${list.size} param = $id")
            list.first { krok ->
                krok.id_point == id
            }
        }
}
