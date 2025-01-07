package com.example.tsdemoapp.model

data class UserProfile(
    val firstName: String,
    val lastName: String,
    val email: String,
    val avatar: String,
    val dob: String,
    val gender: String,
    val province: String,
    val district: String,
    val occupation: String,
    val nationality: String,
    val monthlyIncome: String,
) : java.io.Serializable