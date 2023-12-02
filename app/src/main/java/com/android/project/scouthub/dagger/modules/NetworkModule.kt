package com.android.project.scouthub.dagger.modules

import com.android.project.scouthub.api.GitHubApi
import com.android.project.scouthub.dagger.scope.AppScope
import com.android.project.scouthub.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @AppScope
    @Provides
    fun providesRetrofit(): Retrofit {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @AppScope
    @Provides
    fun providesGithubApi(retrofit: Retrofit) = retrofit.create(GitHubApi::class.java)

}