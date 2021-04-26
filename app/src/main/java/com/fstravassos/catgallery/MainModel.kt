package com.fstravassos.catgallery

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject
import java.util.stream.Collectors

class MainModel {
    private val TAG = MainModel::class.simpleName

    val mCats = MutableLiveData<List<String>>()

    /**
     * Request list of picture links
     */
    fun picturesRequest(app: Application) {
        val queue = Volley.newRequestQueue(app)

        val request: JsonObjectRequest = object : JsonObjectRequest(Request.Method.GET,
            EServerParams.URL.value, null, listenerPicturesRequest(),
            Response.ErrorListener {
                Log.e(TAG, "Connection fail: " + it.networkResponse.statusCode)
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val params: HashMap<String, String> = HashMap()
                params.put("Authorization", EServerParams.AUTHORIZATION.value)
                return params
            }
        }

        queue.add(request)
    }

    /**
     * Listener of successfully of pictures request
     *
     * @return Listener<JSONObject>
     */
    private fun listenerPicturesRequest(): Response.Listener<JSONObject> {
        return Response.Listener {
            val obj = Gson().fromJson(it.toString(), Data::class.java)

            if (obj != null) {
                val list = obj.data.asList()
                val listOfImages = list.stream().filter { !it.images.isNullOrEmpty() }
                    .map { it.images }.collect(Collectors.toList<Array<Image>>())

                val listOfLinks = listOfImages.stream().map {
                    it.asList().stream().filter { !it.link.isNullOrEmpty() }.map { it.link }
                        .collect(Collectors.toList<String>()) }
                    .collect(Collectors.toList<List<String>>()).flatten()

                mCats.postValue(listOfLinks)
            }
        }
    }

    private enum class EServerParams(var value: String) {
        URL("https://api.imgur.com/3/gallery/search/?q=cats"),
        AUTHORIZATION("Client-ID 1ceddedc03a5d71")
    }
}

class Data(var data: Array<Item>)

class Item(var images: Array<Image>)

class Image(var link: String?)
