package com.krodas.localimage.ui.main

import android.app.Activity
import androidx.annotation.UiThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.bumptech.glide.load.HttpException
import com.krodas.localimage.handler.ResultOf
import com.krodas.localimage.model.Post
import com.krodas.localimage.repository.LocalService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel : ViewModel() {

    val posts = MutableLiveData<ResultOf<List<Post>>>()
    var service = LocalService()

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