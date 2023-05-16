package fi.tuni.userapi.controllers

import fi.tuni.userapi.models.User
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URL
import kotlin.concurrent.thread


var client: OkHttpClient = OkHttpClient();

// connection to dummyjson using okhttp
fun getRequest(sUrl: String): String? {
    var result: String? = null
    try {
        // Create URL
        val url = URL(sUrl)
        // Build request
        val request = Request.Builder().url(url).build()
        // Execute request
        val response = client.newCall(request).execute()
        result = response.body?.string()
    }
    catch(err:Error) {
        print("Error when executing get request")
    }
    return result
}


// sends delete request
fun deleteRequest(id: Int) {
    thread {

        // Create URL
        val url = URL ("https://dummyjson.com/users/${id}")

        // Build request
        val request = Request.Builder().url(url).delete().build()

        // Execute request
        val response = client.newCall(request).execute()

        response.close()
    }
}

