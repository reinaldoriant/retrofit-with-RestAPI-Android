package com.blank.ch6_retrofit.data

import okhttp3.ResponseBody
import org.json.JSONObject

fun ResponseBody.getErrorMessage(): String? {
    return try {
        val jsonObject = JSONObject(string())
        jsonObject.getString("errors")
    } catch (e: Exception) {
        e.message
    }
}