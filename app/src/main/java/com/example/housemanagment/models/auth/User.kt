package com.example.housemanagment.models.auth

data class User(
    val created_at: String?=null,
    val email: String?=null,
    val email_verified_at: String?=null,
    val id: Int?=null,
    val name: String?=null,
    val role_as: String?=null,
    val updated_at: String?=null
)