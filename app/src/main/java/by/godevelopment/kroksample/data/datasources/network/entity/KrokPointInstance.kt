package by.godevelopment.kroksample.data.datasources.network.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class KrokPointInstance(
    val city: City,
    val id: Int,
    val lat: Double,
    val lng: Double,
    val point_images: List<PointImage>,
    val point_key_name: String,
    val tags: List<Tag>,
    val url: String
)

@JsonClass(generateAdapter = true)
data class City(
    val id: Int
)

@JsonClass(generateAdapter = true)
data class PointImage(
    val file: String
)

@JsonClass(generateAdapter = true)
data class Tag(
    val id: Int,
    val tag_key: String
)

// {
//        "id": 29,
//        "url": "http://krokapp.by/api/rest/points/29/",
//        "point_key_name": "Аптэка-музей",
//        "lat": 53.132664,
//        "lng": 26.043444,
//        "point_images": [
//            {
//                "file": "https://krokapp.by/media/pi/2eb3624c-45f7-4f9b-b52e-9b6c2ce28a28.jpg"
//            }
//        ],
//        "tags": [
//            {
//                "id": 29,
//                "tag_key": "museum"
//            }
//        ],
//        "city": {
//            "id": 9
//        }
//    }
