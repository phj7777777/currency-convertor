package com.example.convertor.helper
import com.example.convertor.model.Rate
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException



private val client = OkHttpClient()

fun getRequest(url: String, result: com.example.convertor.Result){
    val request = Request.Builder()
        .url(url)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }
        override fun onResponse(call: Call, response: Response) {

            try{
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    val gson = GsonBuilder().create()
                    var res = gson.fromJson(response.body!!.string(), Rate::class.java)
                    result.getResult(res);
                }
            }catch (e: Error){
                e.printStackTrace()
            }
        }
    })
}

