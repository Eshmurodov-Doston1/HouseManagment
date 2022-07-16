package com.example.housemanagment.models.auth

data class AuthResponse(
    val token: String?=null,
    val user: User?=null
)