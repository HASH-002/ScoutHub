package com.android.project.scouthub.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.project.scouthub.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateUser(user: User): Long

    @Query("SELECT * FROM users")
    fun getAllUsers(): LiveData<List<User>>

    @Delete
    suspend fun deleteUser(user: User)
}