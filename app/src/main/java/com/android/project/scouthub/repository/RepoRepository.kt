package com.android.project.scouthub.repository

import com.android.project.scouthub.api.GitHubApi
import javax.inject.Inject

class RepoRepository @Inject constructor(
    private val gitHubApi: GitHubApi
) {
    suspend fun getUserRepos(username: String) = gitHubApi.getUserRepos(username)

}