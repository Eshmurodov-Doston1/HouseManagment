package com.example.housemanagment.database.repository.buildingRepo

import com.example.housemanagment.database.buildingCompany.BuildingDao
import com.example.housemanagment.database.buildingCompany.BuildingEntity
import javax.inject.Inject

class BuildingRepo @Inject constructor(
    private val buildingDao: BuildingDao
) {
    suspend fun saveBuilding(list: List<BuildingEntity>){
        buildingDao.saveBuilding(list)
    }
    suspend fun deleteBuilding(){
        buildingDao.deleteTableBuilding()
    }
    fun getAllBuildingCompany():List<BuildingEntity>{
        return buildingDao.getAllBuilding()
    }
}