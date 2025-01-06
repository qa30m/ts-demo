package com.example.tsdemoapp.services.profile

import com.example.tsdemoapp.response.ProfileResponse
import retrofit2.Call
import retrofit2.http.GET

interface ProfileApi {
    @GET("v1/m/profile")
    fun updateProfile(): Call<ProfileResponse>
}
