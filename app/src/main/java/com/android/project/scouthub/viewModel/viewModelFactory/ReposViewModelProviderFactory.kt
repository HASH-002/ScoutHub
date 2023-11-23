package com.android.project.scouthub.viewModel.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.project.scouthub.repository.RepoRepository
import com.android.project.scouthub.viewModel.RepoViewModel

class ReposViewModelProviderFactory(
    val repoRepository: RepoRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RepoViewModel(repoRepository) as T
    }
}
