package com.android.project.scouthub.repository

import com.android.project.scouthub.api.RetrofitInstance
import com.android.project.scouthub.db.UserDatabase
import com.android.project.scouthub.model.User

class UsersRepository(
    val db: UserDatabase
) {
    suspend fun searchUser(username: String) = RetrofitInstance.api.searchUser(username)

    suspend fun insertOrUpdateUser(user: User) = db.getUserDao().insertOrUpdateUser(user)
    fun getSavedUsers() = db.getUserDao().getAllUsers()
    suspend fun deleteUser(user: User) = db.getUserDao().deleteUser(user)
}