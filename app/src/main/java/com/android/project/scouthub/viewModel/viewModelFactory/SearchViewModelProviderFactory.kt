package com.android.project.scouthub.viewModel.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.project.scouthub.repository.UsersRepository
import com.android.project.scouthub.viewModel.SearchViewModel
import javax.inject.Inject
import javax.inject.Provider

class SearchViewModelProviderFactory @Inject constructor(
    val usersRepositoryProvider: Provider<UsersRepository>
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(usersRepositoryProvider.get()) as T
    }
}