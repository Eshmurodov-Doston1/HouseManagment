package com.example.housemanagment.models.flatData

data class Home(
    val agreement_id: Int,
    val client_id: Int,
    val created_at: String,
    val id: Int,
    val is_active: Int,
    val loan: String,
    val month_id: String,
    val paid: String,
    val pending: String,
    val updated_at: String
)