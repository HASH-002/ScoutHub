package com.android.project.scouthub.dagger.modules

import android.content.Context
import com.android.project.scouthub.db.UserDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun provideUserDatabase(context: Context): UserDatabase {
        return UserDatabase.invoke(context)
    }

}