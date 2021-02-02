package com.blank.ch6_retrofit.data.model


import com.google.gson.annotations.SerializedName

data class RequestLogin(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)