package com.example.housemanagment.models.soldData

import java.io.Serializable

data class Sold(
    val blok: Blok?=null,
    val blok_id: Int,
    val building: Building?=null,
    val building_id: Int,
    val client: Client?=null,
    val client_id: Int,
    val created_at: String,
    val currency: Int,
    val dom: Dom?=null,
    val dom_id: Int,
    val home_id: Int,
    val house: House?=null,
    val id: Int,
    val initial_paid: String,
    val is_active: Int,
    val loan: String,
    val month: Int,
    val number: String,
    val paid: String,
    val payment_type: String,
    val status: Int,
    val summa: String,
    val updated_at: String,
    val usd: Int
):Serializable