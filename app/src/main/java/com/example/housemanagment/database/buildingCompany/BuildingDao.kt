package com.example.housemanagment.database.buildingCompany

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BuildingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBuilding(list: List<BuildingEntity>)

    @Query("DELETE FROM buildingentity")
    suspend fun deleteTableBuilding()

    @Query("SELECT*FROM buildingentity")
    fun getAllBuilding():List<BuildingEntity>
}