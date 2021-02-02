package com.blank.ch6_retrofit.ui.regis

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blank.ch6_retrofit.data.getErrorMessage
import com.blank.ch6_retrofit.data.model.*
import com.blank.ch6_retrofit.data.remote.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException


class RegisViewModel(private val service: ApiService) : ViewModel() {

    private var disposable: Disposable? = null
    val resultPost = MutableLiveData<RegisMsg>()
    val resultLogin = MutableLiveData<LoginMsg>()
    val resultMe = MutableLiveData<MeMsg>()

    fun register(requestRegis: RequestRegis) {
        disposable = service.regis(requestRegis)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                resultPost.value = it
            }, {
                it.printStackTrace()
            })
    }

    fun showMeData(token: String) {
        disposable = service.me(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                resultMe.value = it
            }, {
                it.printStackTrace()
            })
    }

    fun login(requestLogin: RequestLogin) {
        disposable = service.login(requestLogin)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                resultLogin.value = it
            }, {
                if (it is HttpException) {
                    val msg = it.response()?.errorBody()?.let { it1 -> it1.getErrorMessage() }
                    Log.e("ErrorLogin",msg.toString())
                }
                it.printStackTrace()
            })
    }



    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

    class Factory(private val service: ApiService) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RegisViewModel(service) as T
        }
    }
}