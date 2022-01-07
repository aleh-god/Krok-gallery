package by.godevelopment.kroksample.ui.listcities

import androidx.lifecycle.ViewModel
import by.godevelopment.kroksample.data.TestData
import by.godevelopment.kroksample.domain.model.ListItemModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ListCitiesViewModel @Inject constructor(

) : ViewModel() {
    var onClickNav: MutableStateFlow<(Int) -> Unit> = MutableStateFlow { }

    val out = TestData.listCities.map { list ->
        list.map { listItem ->
            ListItemModel(
                listItem.pictures,
                listItem.text,
                onClickNav.value
            )
        }
    }

    // model flow
    val header = MutableStateFlow(TestData.header)
}