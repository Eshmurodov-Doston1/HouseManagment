package com.example.housemanagment.models.flat

import java.io.Serializable

data class Flat(
    val blok: String,
    val floor: String,
    val home_count: Int,
    val id: Int,
    val name: String,
    val summa: Double
):Serializable