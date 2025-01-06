package com.example.tsdemoapp.response


import com.example.tsdemoapp.model.UserProfile

data class ProfileResponse(
    val status: Int,
    val message: String,
    val profile: UserProfile,
)
