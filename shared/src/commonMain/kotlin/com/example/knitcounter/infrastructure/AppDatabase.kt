package com.example.knitcounter.infrastructure

import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import androidx.room3.RoomDatabaseConstructor
import com.example.knitcounter.App
import com.example.knitcounter.infrastructure.daos.PatternDao
import com.example.knitcounter.infrastructure.entities.GaugeEntity
import com.example.knitcounter.infrastructure.entities.PatternEntity
import com.example.knitcounter.infrastructure.entities.PatternWithGauges

@Database(entities = [
    PatternEntity::class,
    GaugeEntity::class
], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getPatternDao() : PatternDao
}

@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}