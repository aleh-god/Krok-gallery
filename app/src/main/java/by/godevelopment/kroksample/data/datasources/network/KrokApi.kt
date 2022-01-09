package by.godevelopment.kroksample.data.datasources.network

import by.godevelopment.kroksample.data.datasources.network.entity.KrokCity
import by.godevelopment.kroksample.data.datasources.network.entity.KrokView
import retrofit2.Response
import retrofit2.http.GET

interface KrokApi {

    @GET("api/get_cities/11/")
    suspend fun getAllCities(): Response<List<KrokCity>>

    @GET("api/get_points/11/")
    suspend fun getAllViews(): Response<List<KrokView>>

//    @GET("view_place/{place}/")
//    suspend fun getKrokPointList(@Path("place") krokPlace: Int): Response<List<KrokPointInstance>>
//
//    @GET("view/{id}/")
//    suspend fun getKrokView(@Path("id") id: Int): Response<KrokViewInstance>
}

/**
 https://krokapp.com/view_places/  1..6
 https://krokapp.com/view_regions_cities/{6}/  Бресткая область лист
 https://krokapp.com/view_place/{3}/ Брест лист
 https://krokapp.com/view/{7}/ Аллея литературных фонарей

 kropka rest
 https://krokapp.by/api/rest/points/29/

 Запрасіць спіс гарадоў - https://krokapp.by/api/get_cities/11/
 Запрасіць спіс кропак - http://krokapp.by/api/get_points/11/
 Запрасіць спіс моў - https://krokapp.by/api/get_languages
 Запрасіць спіс тэгаў - https://krokapp.by/api/get_tags
 **/