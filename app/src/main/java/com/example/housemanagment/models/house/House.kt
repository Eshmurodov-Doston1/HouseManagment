package com.example.housemanagment.models.house

import java.io.Serializable

data class House(
    val area: String,
    val created_at: String,
    val dom_id: Int,
    val floor: Int,
    val id: Int,
    val number: String,
    val rooms: String,
    val status: Int,
    val updated_at: String
):Serializable