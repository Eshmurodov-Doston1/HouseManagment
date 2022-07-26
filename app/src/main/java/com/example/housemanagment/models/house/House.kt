package com.example.housemanagment.models.house

import java.io.Serializable

data class House(
    val area: String,
    val blok: String,
    val build: String?,
    val dom: String,
    val floor: Int,
    val id: Int,
    val number: String,
    val paid: Double,
    val rooms: String,
    val status: Int,
    val summa: Double
):Serializable