package com.android.project.scouthub.repository

import com.android.project.scouthub.api.GitHubApi
import com.android.project.scouthub.db.UserDatabase
import com.android.project.scouthub.model.User
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val db: UserDatabase,
    private val gitHubApi: GitHubApi
) {
    suspend fun searchUser(username: String) = gitHubApi.searchUser(username)

    suspend fun insertOrUpdateUser(user: User) = db.getUserDao().insertOrUpdateUser(user)
    fun getSavedUsers() = db.getUserDao().getAllUsers()
    suspend fun deleteUser(user: User) = db.getUserDao().deleteUser(user)
}