package com.android.project.scouthub.repository

import com.android.project.scouthub.api.RetrofitInstance

class RepoRepository {
    suspend fun getUserRepos(username: String) = RetrofitInstance.api.getUserRepos(username)

}