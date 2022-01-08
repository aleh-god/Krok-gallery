package by.godevelopment.kroksample.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import by.godevelopment.kroksample.R

enum class Region(
    val id: Int,
    @StringRes
    val text: Int,
    @DrawableRes
    val drawableRes: Int,
    val pictures: String
) {
    Minsk(1, R.string.minsk_region_const, R.drawable.minsk_region,"https://krokapp.by/media/regions/566ea590-c705-416b-9257-b8ceaef47849.png"),
    Vitebsk(2, R.string.vitebsk_region_const, R.drawable.vitebsk_region, "https://krokapp.by/media/regions/08dd0030-72b0-474e-abd0-fdc4c30033d3.png"),
    Gomel(3, R.string.gomel_region_const, R.drawable.gomel_region,"https://krokapp.by/media/regions/95e91be1-c182-49cb-b0aa-a66d30ae0352.png"),
    Grodno(4, R.string.grodno_region_const, R.drawable.grodno_region,"https://krokapp.by/media/regions/6aa7e851-2582-4178-9cb0-a874443d5dbe.png"),
    Mogilev(5, R.string.mogilev_region_const, R.drawable.mogilev_region,"https://krokapp.by/media/regions/881a1cae-b16a-4b54-a92e-7d6838d46ef9.png"),
    Brest(6, R.string.brest_region_const, R.drawable.brest_region, "https://krokapp.by/media/regions/21b19d7c-9774-45b0-986a-c67b9dcd6895.png")
}