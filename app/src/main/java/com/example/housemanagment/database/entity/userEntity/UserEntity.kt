package com.example.housemanagment.database.entity.userEntity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity (
    val created_at: String?,
    val email: String,
    val email_verified_at: String,
    @PrimaryKey
    val id: Int,
    val name: String,
    val role_as: String,
    val updated_at: String?
)