package by.godevelopment.kroksample.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import by.godevelopment.kroksample.R

enum class Region(
    val id: Int,
    @StringRes
    val text: Int,
    @DrawableRes
    val drawableRes: Int
) {
    Minsk(1, R.string.minsk_region_const, R.drawable.minsk_region),
    Vitebsk(2, R.string.vitebsk_region_const, R.drawable.vitebsk_region),
    Gomel(3, R.string.gomel_region_const, R.drawable.gomel_region),
    Grodno(4, R.string.grodno_region_const, R.drawable.grodno_region),
    Mogilev(5, R.string.mogilev_region_const, R.drawable.mogilev_region),
    Brest(6, R.string.brest_region_const, R.drawable.brest_region);

    companion object {
        fun getRegionNameById(key: Int): Region? = Region.values().find { it.id == key }
    }
}