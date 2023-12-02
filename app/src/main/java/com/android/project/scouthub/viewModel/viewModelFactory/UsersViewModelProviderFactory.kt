package com.android.project.scouthub.viewModel.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.project.scouthub.repository.UsersRepository
import com.android.project.scouthub.viewModel.UsersViewModel
import javax.inject.Inject
import javax.inject.Provider

class UsersViewModelProviderFactory @Inject constructor(
    val usersRepositoryProvider: Provider<UsersRepository>
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UsersViewModel(usersRepositoryProvider.get()) as T
    }
}