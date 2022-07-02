package ru.geekbrains.myappmaterialdesign.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.geekbrains.myappmaterialdesign.utils.myApiKey
import ru.geekbrains.myappmaterialdesign.utils.myDate
import ru.geekbrains.myappmaterialdesign.utils.planetaryApod

interface PictureOfTheDayAPI {

    @GET(planetaryApod)
    fun getPictureOfTheDay(@Query(myApiKey) apiKey: String): Call<PictureOfTheDayResponseData>

    @GET(planetaryApod)
    fun getPictureOfTheDayByDate(
        @Query(myApiKey) apiKey: String,
        @Query(myDate) date: String
    ): retrofit2.Call<PictureOfTheDayResponseData>

}