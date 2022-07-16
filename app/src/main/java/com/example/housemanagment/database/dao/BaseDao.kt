package com.example.housemanagment.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface BaseDao<T> {
    @Insert
    fun insert(table: T): Long

    @Insert
    fun insert(vararg table: T): List<Long>

    @Update
    fun update(table: T): Int

    @Delete
    fun delete(table: T): Int
}