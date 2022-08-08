package com.example.housemanagment.database.repository

import com.example.housemanagment.database.dao.UserDao
import com.example.housemanagment.database.entity.userEntity.UserEntity
import javax.inject.Inject

class AuthDaoRepository @Inject constructor(
    private val userDao: UserDao
) {
    /** userDao **/
    fun saveUser(userEntity: UserEntity) =
        userDao.insert(userEntity)


    fun updateUserEntity(userEntity: UserEntity) =
        userDao.update(userEntity)

    fun deleteUserEntity(userEntity: UserEntity) =
        userDao.delete(userEntity)

    fun getUserEntity():UserEntity = userDao.getUserEntity()
    /** userDao **/
    fun deleteTableUser() = userDao.deleteTable()
}