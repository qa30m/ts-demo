package com.example.tsdemoapp.network

import com.example.tsdemoapp.preferences.UserPreferences
import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import okhttp3.OkHttpClient
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import java.security.SecureRandom
import okhttp3.Request
import javax.net.ssl.HostnameVerifier
import kotlin.toString

object  ApiClientWithToken{
    private const val BASE_URL = "https://dms.afghantelecom.af/api/"

    // UserPreferences requires a context to be instantiated
    private lateinit var userPreferences: UserPreferences

    var bearerToken: String = "" // Initialize bearerToken directly

    fun init(context: Context) {
        // Initialize the class-level userPreferences
        userPreferences = UserPreferences(context)
        bearerToken = userPreferences.getUser()?.token.toString() // Set the bearerToken

        Log.d("ApiClientWithToken", bearerToken) // Log the bearerToken directly
    }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getUnsafeOkHttpClient(bearerToken)) // Use the unsafe OkHttpClient
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    private fun getUnsafeOkHttpClient(token: String): OkHttpClient {
        try {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            })

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory

            val builder = OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                .hostnameVerifier(HostnameVerifier { _, _ -> true })
                .addInterceptor(Interceptor { chain ->
                    val original: Request = chain.request()
                    val requestBuilder: Request.Builder = original.newBuilder()
                        .header("Authorization", "Bearer $token") // Add Bearer token here
                    val request: Request = requestBuilder.build()
                    chain.proceed(request)
                })

            return builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
