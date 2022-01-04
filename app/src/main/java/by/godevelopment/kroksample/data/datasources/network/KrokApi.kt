package by.godevelopment.kroksample.data.datasources.network

import by.godevelopment.kroksample.data.datasources.network.entity.KrokPointInstance
import by.godevelopment.kroksample.data.datasources.network.entity.KrokViewInstance
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface KrokApi {
    @GET("view_place/{place}/")
    suspend fun getKrokPointList(@Path("place") krokPlace: Int): Response<List<KrokPointInstance>>

    @GET("view/{id}/")
    suspend fun getKrokView(@Path("id") id: Int): Response<KrokViewInstance>
}

// Напрыклад, для Мінска патрэбна вывесьці ўсе гэтыя кропкі - https://krokapp.com/view_place/9/
// у такім фармаце - https://krokapp.com/view/29/

//Запрасіць спіс гарадоў - https://krokapp.by/api/get_cities/11/
//Запрасіць спіс кропак - http://krokapp.by/api/get_points/11/
//Запрасіць спіс моў - https://krokapp.by/api/get_languages
//Запрасіць спіс тэгаў - https://krokapp.by/api/get_tags