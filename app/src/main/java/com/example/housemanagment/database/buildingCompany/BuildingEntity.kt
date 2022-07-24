package com.example.housemanagment.database.buildingCompany

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BuildingEntity (
    @PrimaryKey
    var id:Long=0,
    var name:String,
    var address:String,
    var summa:String,
    var blok_count:Long)