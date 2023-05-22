package fi.tuni.userapi.controllers

import fi.tuni.userapi.models.User
import okhttp3.*
import java.net.URL
import kotlin.concurrent.thread


var client: OkHttpClient = OkHttpClient()

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
        //println(response.body?.string())
        response.close()
    }
}

fun postRequest(user: User) {
    thread {
        val url = URL("https://dummyjson.com/users/add")
        // add user information to post form and build formbody

        val formBody: RequestBody = FormBody.Builder()
            .add("firstName", user.firstName.toString())
            .add("lastName", user.lastName.toString())
            .add("phone", user.phone.toString())
            .build()

        // Build request
        val request: Request = Request.Builder().url(url).post(formBody).build()

        // Execute request
        val response = client.newCall(request).execute()
        //println(response.body?.string())

        response.close()
    }
}