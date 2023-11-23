package com.android.project.scouthub.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.project.scouthub.model.User
import com.android.project.scouthub.repository.UsersRepository
import com.android.project.scouthub.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class UsersViewModel(
    val userRepository: UsersRepository
) : ViewModel() {

    val searchUser: MutableLiveData<Resource<User>> = MutableLiveData()

    fun searchUser(username: String) = viewModelScope.launch {
        searchUser.postValue(Resource.Loading())
        val response = userRepository.searchUser(username)
        searchUser.postValue(handleSearchedUserResponse(response))
    }

    private fun handleSearchedUserResponse(response: Response<User>) : Resource<User> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveUser(user: User) = viewModelScope.launch {
        userRepository.insertOrUpdateUser(user)
    }
    fun getSavedUsers() = userRepository.getSavedUsers()
    fun deleteUser(user: User) = viewModelScope.launch {
        userRepository.deleteUser(user)
    }
}