package com.blank.ch6_retrofit.ui.upload

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blank.ch6_retrofit.data.model.ResponseUploadImg
import com.blank.ch6_retrofit.data.remote.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

const val token =
    "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MDE0MjEzMWQ2ODIyYTAwMTdjZGYwYTIiLCJ1c2VybmFtZSI6Imdob3ppbWFuIiwiZW1haWwiOiJnaG96aWNveUBnbWFpbC5jb20iLCJpYXQiOjE2MTIyNjk2MDIsImV4cCI6MTYxMjI3NjgwMn0.5LImP99FkxZlJs0XaWJbEkKdDICmgh9ZoJzyogVnswo"

class UploadViewModel(private val service: ApiService) : ViewModel() {

    private var disposable: CompositeDisposable = CompositeDisposable()
    val resultPost = MutableLiveData<ResponseUploadImg>()

    fun upload(username: String, email: String, file: File) {
        val filePart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            file.name,
            file.asRequestBody("image/*".toMediaTypeOrNull())
        )

//        val filePart = file.asRequestBody("image/*".toMediaTypeOrNull())

        val usernamePart: RequestBody = username.toRequestBody("multipart/form-data".toMediaType())
        val emailPart: RequestBody = email.toRequestBody("multipart/form-data".toMediaType())

        disposable.addAll(
            service.uploadImage(token, usernamePart, emailPart, filePart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    resultPost.value = it
                }, {
                    it.printStackTrace()
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    class Factory(private val service: ApiService) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UploadViewModel(service) as T
        }
    }
}