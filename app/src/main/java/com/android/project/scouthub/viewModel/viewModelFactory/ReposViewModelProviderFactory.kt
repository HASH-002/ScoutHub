package com.android.project.scouthub.viewModel.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.project.scouthub.repository.RepoRepository
import com.android.project.scouthub.viewModel.RepoViewModel
import javax.inject.Inject
import javax.inject.Provider

class ReposViewModelProviderFactory @Inject constructor(
    val repoRepositoryProvider: Provider<RepoRepository>
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RepoViewModel(repoRepositoryProvider.get()) as T
    }
}
