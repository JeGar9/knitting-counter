package com.example.knitcounter.domain

import kotlin.time.Instant

enum class GaugeType(val width: Length,
                     val height: Length)
{
    TEN_CENTIMETERS(Length.cm(10), Length.cm(10)),
    FOUR_INCHES(Length.inches(4), Length.inches(4)),
}

class Gauge (
    val numStitches: Int,
    val numRows: Int,
    val type: GaugeType
)

class Pattern (
    val id: Long = 0,
    val title: String,
    val gauge: Map<GaugeType, Gauge>,
    val file: String,
    val createdAt: Instant,
    val updatedAt: Instant
)