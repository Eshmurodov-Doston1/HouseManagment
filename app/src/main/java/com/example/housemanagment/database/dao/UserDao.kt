package com.example.housemanagment.database.dao

import androidx.room.*
import com.example.housemanagment.database.entity.userEntity.UserEntity

@Dao
interface UserDao {
    @Insert
    fun insert(table: UserEntity)

    @Insert
    fun insert(vararg table: UserEntity)

    @Update
    fun update(table: UserEntity)

    @Delete
    fun delete(table: UserEntity)

    @Query("SELECT*FROM UserEntity")
    @JvmSuppressWildcards
    fun getUserEntity():UserEntity

    @Query("DELETE FROM UserEntity")
    fun deleteTable()

}