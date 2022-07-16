package com.example.housemanagment.database.repository

import com.example.housemanagment.database.dao.UserDao
import com.example.housemanagment.database.entity.userEntity.UserEntity
import javax.inject.Inject

class AuthDaoRepository @Inject constructor(
    private val userDao: UserDao
) {
    /** userDao **/
    suspend fun saveUser(userEntity: UserEntity) =
        userDao.insert(userEntity)


    suspend fun updateUserEntity(userEntity: UserEntity) =
        userDao.update(userEntity)

    suspend fun deleteUserEntity(userEntity: UserEntity) =
        userDao.delete(userEntity)

    fun getUserEntity():UserEntity = userDao.getUserEntity()
    /** userDao **/
    suspend fun deleteTableUser() = userDao.deleteTable()
}