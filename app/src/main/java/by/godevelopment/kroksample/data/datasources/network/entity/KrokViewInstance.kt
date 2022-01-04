package by.godevelopment.kroksample.data.datasources.network.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class KrokViewInstance (
        val city_id: Int,
        val id: Int,
        val id_point: Int,
        val images: List<String>,
        val is_excursion: Boolean,
        val lang: Int,
        val last_edit_time: Int,
        val lat: Double,
        val lng: Double,
        val logo: String,
        val name: String,
        val photo: String,
        val sound: String,
        val tags: List<Int>,
        val text: String,
        val visible: Boolean
)

//