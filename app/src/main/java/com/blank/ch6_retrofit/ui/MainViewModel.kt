package com.blank.ch6_retrofit.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blank.ch6_retrofit.data.model.PostMsg
import com.blank.ch6_retrofit.data.remote.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val service: ApiService) : ViewModel() {

    private var disposable: Disposable? = null
    val resultPost = MutableLiveData<PostMsg>()

    fun getPost() {
        disposable = service.getPost()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                resultPost.value = it
            }, {
                it.printStackTrace()
            })
    }

    fun munculLagiKamu() {
        resultPost.value = PostMsg()
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

    class Factory(private val service: ApiService) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(service) as T
        }
    }
}