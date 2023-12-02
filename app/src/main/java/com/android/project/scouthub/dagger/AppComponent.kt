package com.android.project.scouthub.dagger

import com.android.project.scouthub.dagger.modules.AppModule
import com.android.project.scouthub.dagger.modules.DatabaseModule
import com.android.project.scouthub.dagger.modules.NetworkModule
import com.android.project.scouthub.dagger.scope.AppScope
import dagger.Component

@AppScope
@Component(modules=[
    AppModule::class,
    NetworkModule::class,
    DatabaseModule::class])
interface AppComponent {

    fun newActivityComponentBuilder(): ActivityComponent.Builder

}