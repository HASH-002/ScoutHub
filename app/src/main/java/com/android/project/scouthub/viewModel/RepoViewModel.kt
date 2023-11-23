package com.android.project.scouthub.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.project.scouthub.model.Repo
import com.android.project.scouthub.repository.RepoRepository
import com.android.project.scouthub.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class RepoViewModel(
    val repoRepository: RepoRepository
) : ViewModel(){

    val getUserRepos: MutableLiveData<Resource<Repo>> = MutableLiveData()

    fun getUserRepos(username: String) = viewModelScope.launch {
        getUserRepos.postValue(Resource.Loading())
        val response = repoRepository.getUserRepos(username)
        getUserRepos.postValue(handleUserReposResponse(response))
    }

    private fun handleUserReposResponse(response: Response<Repo>) : Resource<Repo> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}