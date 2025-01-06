package com.example.tsdemoapp.viewModal.auth.login

import com.example.tsdemoapp.response.ProfileResponse
import com.example.tsdemoapp.repository.Result
import android.content.Context
import android.util.Log
import com.example.tsdemoapp.network.ApiClientWithToken
import com.example.tsdemoapp.services.profile.ProfileApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ProfileRepository (private val context: Context) {

    init {
        ApiClientWithToken.init(context)
    }
    private val apiService = ApiClientWithToken.retrofit.create(ProfileApi::class.java)

    fun UpdateProfile(callback: ProfileCallback) {
        apiService.updateProfile().enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                if (response.isSuccessful) {
                    val profileResponse: ProfileResponse? = response.body()
                    if (profileResponse != null) {
                        Log.d("Profile", "Profile was updated successful ${profileResponse.profile}")
                        callback.onSuccess(Result.Success(profileResponse))
                    } else {
                        Log.e("Profile", "Response body is null")
                        callback.onError(Result.Error(IOException("Response body is null")))
                    }

                } else {
                    Log.e("Profile", "API call unsuccessful: ${response.message()}")
                    callback.onError(Result.Error(IOException("API call unsuccessful: ${response.code()}")))
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                Log.e("Profile", "API call failed: ${t.message}")
                callback.onError(Result.Error(IOException("API call failed", t)))
            }
        })
    }
}

interface ProfileCallback {
    fun onSuccess(result: Result.Success<ProfileResponse>)
    fun onError(result: Result.Error)
}
