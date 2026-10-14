package com.example.knitcounter.domain

enum class GaugeType(val width: Length,
                     val height: Length)
{
    TEN_CENTIMETERS(Length.cm(10), Length.cm(10)),
    FOUR_INCHES(Length.inches(4), Length.inches(4)),
}

@JvmInline
value class FolderId(val id: Long) {
    companion object {
        val ROOT = FolderId(0)
    }
}

data class Folder (
    val id: FolderId,
    var name: String,
    var count: Int,
    val ownerId: UserId,
    var parentFolder: FolderId
)

data class Gauge (
    val numStitches: Int,
    val numRows: Int,
    val type: GaugeType
)

data class Pattern (
    val id: Long = 0,
    val ownerId: UserId,
    var name: String,
    val gauge: Map<GaugeType, Gauge>,
    val file: String,
    val progression: Int,
    val icon: String,
    var parentFolder: FolderId = FolderId.ROOT,
    val tags: List<String> = listOf()
)