package com.example.tsdemoapp.viewmodel

import com.example.tsdemoapp.viewModal.auth.login.ProfileCallback
import com.example.tsdemoapp.viewModal.auth.login.ProfileRepository
import com.example.tsdemoapp.response.ProfileResponse
import com.example.tsdemoapp.repository.Result
import com.example.tsdemoapp.model.UserProfile
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileRepository: ProfileRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _userProfile = MutableLiveData<UserProfile?>()
    val userProfile: LiveData<UserProfile?> get() = _userProfile

    // LiveData for refreshing state
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> get() = _isRefreshing

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            updateUserProfile()
            delay(2000)
            _isRefreshing.value = false
        }
    }

    // Update profile via repository
    fun updateUserProfile() {
        profileRepository.UpdateProfile(object : ProfileCallback {
            override fun onSuccess(result: Result.Success<ProfileResponse>) {
                when (result) {
                    is Result.Success -> {
                        _userProfile.postValue(result.data.profile) // Correctly access the data here
                    }

                }
            }
            override fun onError(result: Result.Error) {
            }
        })
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras,
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])

                // Initialize loginRepository using the application context or any necessary methods
                val profileRepository =
                    ProfileRepository(application)

                // Create a SavedStateHandle for this ViewModel from extras
                val savedStateHandle = extras.createSavedStateHandle()

                return ProfileViewModel(
                    profileRepository,
                    savedStateHandle
                ) as T
            }
        }

    }

}
