package com.fatchickensoftware.books

import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.io.Serializable

data class ImageLinks (
    val smallThumbnail: String? = null,
    val thumbnail: String? = null
) : Serializable

data class VolumeInfo(
    val title: String? = null,
    val subtitle: String? = null,
    val description: String? = null,
    val authors: List<String> = emptyList(),
    val publisher: String? = null,
    val publishedDate: String? = null,
    val imageLinks: ImageLinks? = null
) : Serializable

data class Volume(
    val id: String = "",
    val volumeInfo: VolumeInfo? = null
) : Serializable

data class Volumes(
    val items: List<Volume> = emptyList()
)

typealias SearchCallback = (Volumes?) -> Unit

class BooksAPI(private val client: OkHttpClient) {

    fun search(query: String, startIndex: Int, maxResults: Int, callback: SearchCallback) {
        val url = HttpUrl.Builder()
            .scheme("https")
            .host("www.googleapis.com")
            .addPathSegment("books")
            .addPathSegment("v1")
            .addPathSegment("volumes")
            .addQueryParameter("q", query)
            .addQueryParameter("startIndex", startIndex.toString())
            .addQueryParameter("maxResults", maxResults.toString())
            .build()

        val request = Request.Builder()
            .addHeader("accept", "application/json")
            .addHeader("key", BOOKS_API_KEY)
            .url(url)
            .build()

        asyncRequest(request, callback)
    }

    private inline fun <reified T> asyncRequest(request: Request, crossinline callback: (T?) -> Unit) {
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                when {
                    response.isSuccessful -> callback(Gson().fromJson(response.body()?.string(), T::class.java))
                    else -> callback(null)
                }
            }
        })
    }
}
