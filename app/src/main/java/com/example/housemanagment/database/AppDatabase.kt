package com.example.housemanagment.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.housemanagment.database.dao.UserDao
import com.example.housemanagment.database.entity.userEntity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase:RoomDatabase(){
    abstract fun appDao():UserDao
}