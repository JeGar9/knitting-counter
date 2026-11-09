package com.example.knitcounter.application

import com.example.knitcounter.domain.Pattern
import com.example.knitcounter.domain.PatternCover
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class PatternsService {

    fun getUserPatterns() : Flow<List<Pattern>> = emptyFlow()

    fun getCoverImage(coverImageData: PatternCover.Image) : ByteArray {
        return ByteArray(0)
    }
}