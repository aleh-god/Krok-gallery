package by.godevelopment.kroksample.data

import android.os.Parcel
import android.os.Parcelable
import by.godevelopment.kroksample.domain.model.DetailsModel
import by.godevelopment.kroksample.domain.model.ListItem
import by.godevelopment.kroksample.domain.model.ListItemModel
import by.godevelopment.kroksample.domain.model.RegionItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow

object TestData {

    val resultPlaces = List(10) { i ->
        ListItem("","Places# $i")
    }

    var listRegions = flow {
        kotlinx.coroutines.delay(1000)
        val result = List(10) { i ->
            RegionItem(0, "", "Regions# $i")
        }
        emit(result)
    }

    var listCities = flow {
        kotlinx.coroutines.delay(1000)
        val result = List(10) { i ->
            ListItem("","Cities# $i")
        }
        emit(result)
    }

    var listPlaces: Flow<List<ListItem>> = flow {
        //throw Exception("Hi There!")
        kotlinx.coroutines.delay(1000)
        val result = List(10) { i ->
            ListItem("","Places# $i")
        }
        emit(result)
    }

    val view: Flow<DetailsModel> = flow {
        emit(
            DetailsModel(
                "Галава",
                "Галава тэксту",
                "Вельми вялики тэкст",
                "",
                ""
            )
        )
    }

    const val header = "БЕЛАРУСЬ"
}
