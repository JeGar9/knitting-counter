package com.example.knitcounter.domain

@JvmInline
value class Length private constructor (val millimeters: Double) {

    val centimeters: Double
        get() = millimeters / 10.0

    val inches: Double
        get() = millimeters / 25.4

    companion object {

        fun mm(value: Int) = mm(value.toDouble())
        fun mm(value: Double) = Length(value)

        fun cm(value: Int) = cm(value.toDouble())
        fun cm(value: Double) = Length(value * 10)

        fun inches(value: Int) = inches(value.toDouble())
        fun inches(value: Double) = Length(value * 25.4)
    }
}