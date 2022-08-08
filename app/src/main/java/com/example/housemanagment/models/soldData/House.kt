package com.example.housemanagment.models.soldData

data class House(
    val area: String,
    val created_at: String,
    val dom_id: Int,
    val floor: Int,
    val id: Int,
    val number: String,
    val price: Double,
    val rooms: String,
    val status: Int,
    val updated_at: String
)