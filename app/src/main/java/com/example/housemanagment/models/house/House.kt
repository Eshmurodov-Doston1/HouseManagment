package com.example.housemanagment.models.house

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class House(
    val area: String,
    val blok: String,
    val build: String?,
    val dom: String,
    val floor: Int,
    val id: Int,
    val number: String,
    @SerializedName("paid")
    val paid: Double?=0.0,
    val rooms: String,
    val status: Int,
    @SerializedName("summa")
    val summa: Double?=0.0,
    val phone:String,
    val price:Double
):Serializable