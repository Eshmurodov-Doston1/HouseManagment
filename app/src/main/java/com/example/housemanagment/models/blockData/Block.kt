package com.example.housemanagment.models.blockData

import java.io.Serializable

data class Block(
    val build: String,
    val dom_count: Int,
    val id: Int,
    val name: String,
    val summa: Double
):Serializable