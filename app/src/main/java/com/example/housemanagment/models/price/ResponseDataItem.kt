package com.example.housemanagment.models.price

data class ResponseDataItem(
    val area: String,
    val created_at: String,
    val dom_id: Int,
    val floor: Int,
    val id: Int,
    val number: String,
    val price: String,
    val rooms: String,
    val status: Int,
    val updated_at: String
)