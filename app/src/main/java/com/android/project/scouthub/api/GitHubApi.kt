package com.android.project.scouthub.api

import com.android.project.scouthub.model.Repo
import com.android.project.scouthub.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {

    @GET("users/{username}")
    suspend fun searchUser(@Path("username") username: String): Response<User>

    @GET("users/{username}/repos")
    suspend fun getUserRepos(@Path("username") username: String): Response<Repo>
}