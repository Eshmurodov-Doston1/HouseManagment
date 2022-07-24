package com.example.housemanagment.models.buildingData

import java.io.Serializable

data class Building(
    val address: String,
    val blok_count: Int,
    val id: Int,
    val name: String,
    val summa: Int
):Serializable