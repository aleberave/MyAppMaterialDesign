package ru.geekbrains.myappmaterialdesign.model

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.geekbrains.myappmaterialdesign.utils.nasaBaseUrl

class RepositoryImpl {

    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(nasaBaseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }

    fun getPictureOfTheDayAPI(): PictureOfTheDayAPI {
        return retrofit.create(PictureOfTheDayAPI::class.java)
    }


}