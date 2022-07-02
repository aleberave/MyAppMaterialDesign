package ru.geekbrains.myappmaterialdesign.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.geekbrains.myappmaterialdesign.BuildConfig
import ru.geekbrains.myappmaterialdesign.model.PictureOfTheDayResponseData
import ru.geekbrains.myappmaterialdesign.model.RepositoryImpl

class PictureOfTheDayViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: RepositoryImpl = RepositoryImpl()
) : ViewModel() {

    fun getLiveData(): MutableLiveData<AppState> {
        return liveData
    }

    fun sendRequest() {
        liveData.postValue(AppState.Loading)
        repositoryImpl.getPictureOfTheDayAPI().getPictureOfTheDay(BuildConfig.NASA_API_KEY)
            .enqueue(callback)
    }

    fun sendRequest(date: String) {
        liveData.postValue(AppState.Loading)
        BuildConfig.NASA_API_KEY.let {
            repositoryImpl.getPictureOfTheDayAPI()
                .getPictureOfTheDayByDate(it, date)
                .enqueue(callback)
        }
    }

    private val callback = object : Callback<PictureOfTheDayResponseData> {
        override fun onResponse(
            call: Call<PictureOfTheDayResponseData>,
            response: Response<PictureOfTheDayResponseData>
        ) {
            if (response.isSuccessful) {
                liveData.postValue(AppState.Success(response.body()!!))
            } else {
                liveData.postValue(AppState.Error(Throwable(response.errorBody().toString())))
            }
        }

        override fun onFailure(call: Call<PictureOfTheDayResponseData>, t: Throwable) {
            t.printStackTrace()
        }

    }
}