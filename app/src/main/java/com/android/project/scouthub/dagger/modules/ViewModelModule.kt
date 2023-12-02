package com.android.project.scouthub.dagger.modules

import androidx.lifecycle.ViewModel
import com.android.project.scouthub.dagger.ViewModelKey
import com.android.project.scouthub.viewModel.RepoViewModel
import com.android.project.scouthub.viewModel.SearchViewModel
import com.android.project.scouthub.viewModel.UsersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UsersViewModel::class)
    abstract fun usersViewModel(usersViewModel: UsersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun searchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RepoViewModel::class)
    abstract fun repoViewModel(repoViewModel: RepoViewModel): ViewModel

}