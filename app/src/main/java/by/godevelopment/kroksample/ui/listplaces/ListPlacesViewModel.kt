package by.godevelopment.kroksample.ui.listplaces

import androidx.lifecycle.ViewModel
import by.godevelopment.kroksample.data.TestData
import by.godevelopment.kroksample.domain.model.ListItemModel
import by.godevelopment.kroksample.domain.usecase.GetListCitiesByIdRegionUseCase
import by.godevelopment.kroksample.domain.usecase.GetListViewsByIdCityUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ListPlacesViewModel @Inject constructor(
    private val getListViewsByIdCityUserCase: GetListViewsByIdCityUserCase
) : ViewModel() {
    // input flow
    var onClickNav: MutableStateFlow<(Int) -> Unit> = MutableStateFlow { }
    val idKey = MutableStateFlow(-1)

    val out = idKey.flatMapLatest {
        getListViewsByIdCityUserCase(it, onClickNav.value)
    }

    // model flow
    val header = MutableStateFlow(TestData.header)
}