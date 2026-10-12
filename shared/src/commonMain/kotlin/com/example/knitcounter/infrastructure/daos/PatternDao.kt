package com.example.knitcounter.infrastructure.daos

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Transaction
import androidx.room3.Update
import com.example.knitcounter.infrastructure.entities.GaugeEntity
import com.example.knitcounter.infrastructure.entities.PatternEntity
import com.example.knitcounter.infrastructure.entities.PatternWithGauges
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PatternDao {

     @Insert
     abstract suspend fun insert(pattern: PatternEntity) : Long

     @Insert
     abstract suspend fun insert(gauge: GaugeEntity)

     open suspend fun insert(patternWithGauges: PatternWithGauges) =
          insert(patternWithGauges.patternEntity, patternWithGauges.gauges)

     @Transaction
     open suspend fun insert(pattern: PatternEntity, gauges: List<GaugeEntity>) {
          val patternId = insert(pattern)
          gauges.map { it.copy(patternId = patternId) }.forEach { insert(it) }
     }

     @Update
     abstract suspend fun update(pattern: PatternEntity)

     @Update
     abstract suspend fun update(gauge: GaugeEntity)

     @Delete
     abstract suspend fun delete(pattern: PatternEntity)

     @Delete
     abstract suspend fun delete(gauge: GaugeEntity)

     @Transaction
     @Query("SELECT count(*) from PatternEntity")
     abstract suspend fun count(): Int

     @Transaction
     @Query("SELECT * from PatternEntity")
     abstract fun getAllAsFlow(): Flow<List<PatternWithGauges>>

     @Transaction
     @Query("SELECT * from PatternEntity where id = :id")
     abstract suspend fun getById(id: Long): PatternWithGauges

     @Transaction
     @Query("SELECT * from PatternEntity where title = :title")
     abstract suspend fun getById(title: String): PatternWithGauges
}