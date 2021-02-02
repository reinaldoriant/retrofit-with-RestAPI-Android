package com.blank.ch6_retrofit.data.model


import com.google.gson.annotations.SerializedName

data class RequestRegis(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("username")
    val username: String
)