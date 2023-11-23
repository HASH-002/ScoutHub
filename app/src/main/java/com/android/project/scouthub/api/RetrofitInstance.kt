package com.android.project.scouthub.api

import com.android.project.scouthub.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        private val retrofit by lazy {
            // Create a logging interceptor for logging HTTP request/response details.
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            // Create an OkHttpClient with the logging interceptor.
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            // Create and configure a Retrofit instance.
            Retrofit.Builder()
                .baseUrl(BASE_URL)  // Set the base URL for the API.
                .addConverterFactory(GsonConverterFactory.create())  // Use Gson for JSON serialization/deserialization.
                .client(client)  // Set the OkHttpClient.
                .build()
        }

        // This property provides access to the Retrofit instance, making it a Singleton.
        val api: GitHubApi by lazy {
            retrofit.create(GitHubApi::class.java)
        }
    }
}
