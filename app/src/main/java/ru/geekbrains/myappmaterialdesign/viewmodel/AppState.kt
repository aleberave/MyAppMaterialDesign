package ru.geekbrains.myappmaterialdesign.viewmodel

import ru.geekbrains.myappmaterialdesign.model.PictureOfTheDayResponseData

sealed class AppState {
    object Loading : AppState()
    data class Success(val pictureOfTheDayDTO: PictureOfTheDayResponseData) : AppState()
    data class Error(val error: Throwable) : AppState()
}