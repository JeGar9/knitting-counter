package com.example.knitcounter.infrastructure.entities

import androidx.room3.Embedded
import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.PrimaryKey
import androidx.room3.Relation
import com.example.knitcounter.domain.Gauge


@Entity(
    primaryKeys = ["patternId", "type"],
    foreignKeys = [
        ForeignKey(
            entity = PatternEntity::class,
            parentColumns = ["id"],
            childColumns = ["patternId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class GaugeEntity(
    val patternId: Long,
    val numStitches: Int,
    val numRows: Int,
    val type: String
)

@Entity
data class PatternEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String
)

data class PatternWithGauges(
    @Embedded val patternEntity: PatternEntity,
    @Relation(
        parentColumns = ["id"],
        entityColumns = ["patternId"]
    )
    val gauges: List<GaugeEntity>
)