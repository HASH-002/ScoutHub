package com.android.project.scouthub.viewModel.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.project.scouthub.repository.UsersRepository
import com.android.project.scouthub.viewModel.UsersViewModel

class UsersViewModelProviderFactory(
    val usersRepository: UsersRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UsersViewModel(usersRepository) as T
    }
}