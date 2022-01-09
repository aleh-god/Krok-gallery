package by.godevelopment.kroksample.ui.listcities

import androidx.lifecycle.ViewModel
import by.godevelopment.kroksample.data.TestData
import by.godevelopment.kroksample.domain.model.ListItemModel
import by.godevelopment.kroksample.domain.model.Region
import by.godevelopment.kroksample.domain.usecase.GetListCitiesByIdRegionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ListCitiesViewModel @Inject constructor(
    private val getListCitiesByIdRegionUseCase: GetListCitiesByIdRegionUseCase
) : ViewModel() {
    // input flow
    var onClickNav: MutableStateFlow<(Int) -> Unit> = MutableStateFlow { }
    val idKey = MutableStateFlow(-1)

    val out = idKey.flatMapLatest { key ->
        getListCitiesByIdRegionUseCase(key, onClickNav.value)
    }

    // model flow
    val header = MutableStateFlow(TestData.header)
}