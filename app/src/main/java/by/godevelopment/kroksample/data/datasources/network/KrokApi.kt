package by.godevelopment.kroksample.data.datasources.network

import by.godevelopment.kroksample.data.datasources.network.entity.KrokCity
import by.godevelopment.kroksample.data.datasources.network.entity.KrokView
import retrofit2.Response
import retrofit2.http.GET

/**
kropka rest
https://krokapp.by/api/rest/points/29/

Запрасіць спіс гарадоў - https://krokapp.by/api/get_cities/11/
Запрасіць спіс кропак - http://krokapp.by/api/get_points/11/
Запрасіць спіс моў - https://krokapp.by/api/get_languages
Запрасіць спіс тэгаў - https://krokapp.by/api/get_tags
 **/

interface KrokApi {
    @GET("api/get_cities/11/")
    suspend fun getAllCities(): Response<List<KrokCity>>

    @GET("api/get_points/11/")
    suspend fun getAllViews(): Response<List<KrokView>>
}
