package com.blank.ch6_retrofit.data.model


import com.google.gson.annotations.SerializedName

class PostMsg : ArrayList<PostMsg.PostMsgItem>() {
    data class PostMsgItem(
        @SerializedName("categories")
        val categories: String,
        @SerializedName("content")
        val content: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("title")
        val title: String
    )
}