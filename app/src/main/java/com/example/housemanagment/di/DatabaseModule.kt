package com.example.housemanagment.di

import android.content.Context
import androidx.room.Room
import com.example.housemanagment.database.AppDatabase
import com.example.housemanagment.database.dao.UserDao
import com.example.housemanagment.utils.AppConstant.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context,AppDatabase::class.java,DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun providesAppDao(appDatabase: AppDatabase):UserDao = appDatabase.appDao()
}