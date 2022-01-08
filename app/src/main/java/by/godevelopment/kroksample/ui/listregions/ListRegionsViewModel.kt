package by.godevelopment.kroksample.ui.listregions

import android.util.Log
import androidx.lifecycle.ViewModel
import by.godevelopment.kroksample.common.TAG
import by.godevelopment.kroksample.data.TestData
import by.godevelopment.kroksample.domain.model.ListItemModel
import by.godevelopment.kroksample.domain.usecase.GetRegionListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ListRegionsViewModel @Inject constructor(
    private val getRegionListUseCase: GetRegionListUseCase
) : ViewModel() {
    var onClickNav: MutableStateFlow<(Int) -> Unit> = MutableStateFlow { }

    val out = getRegionListUseCase.execute()
        .map { list ->
            Log.i(TAG, "ListRegionsViewModel: ${list.toString()}")
        list.map { RegionItem ->
            ListItemModel(
                RegionItem.pictures,
                RegionItem.text,
                onClickNav.value
            )
        }
    }

    // model flow
    val header = MutableStateFlow(TestData.header)
}