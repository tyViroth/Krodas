package com.socialism.app.ui.main

import android.app.Activity
import androidx.annotation.UiThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.HttpException
import com.socialism.app.handler.ResultOf
import com.socialism.app.model.Post
import com.socialism.app.repository.LocalService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel : ViewModel() {

    val posts = MutableLiveData<ResultOf<ArrayList<Post>>>()
    private var service = LocalService()

    @UiThread
    fun fetchPostsFromApi(activity: Activity) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = service.localFile(activity = activity)
                posts.postValue(ResultOf.Success(response))
            } catch (iOException: IOException) {
                posts.postValue(ResultOf.Error(iOException))
            } catch (httpException: HttpException) {
                posts.postValue(ResultOf.Error(httpException))
            }
        }
    }
}