package com.android.project.scouthub

import android.app.Application
import com.android.project.scouthub.dagger.AppComponent
import com.android.project.scouthub.dagger.DaggerAppComponent
import com.android.project.scouthub.dagger.modules.AppModule
import com.android.project.scouthub.dagger.modules.DatabaseModule
import com.android.project.scouthub.dagger.modules.NetworkModule

class MyApplication: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule())
            .databaseModule(DatabaseModule())
            .build()
    }

}