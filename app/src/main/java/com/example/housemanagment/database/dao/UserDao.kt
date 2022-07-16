package com.example.housemanagment.database.dao

import androidx.room.*
import com.example.housemanagment.database.entity.userEntity.UserEntity

@Dao
interface UserDao {
    @Insert
    suspend fun insert(table: UserEntity)

    @Insert
    suspend fun insert(vararg table: UserEntity)

    @Update
    suspend fun update(table: UserEntity)

    @Delete
    suspend fun delete(table: UserEntity)

    @Query("SELECT*FROM UserEntity")
    @JvmSuppressWildcards
    fun getUserEntity():UserEntity

    @Query("DELETE FROM UserEntity")
    suspend fun deleteTable()

}