package com.example.knitcounter.application

import com.example.knitcounter.domain.Pattern
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class PatternsService {

    fun getUserPatterns() : Flow<List<Pattern>> = emptyFlow()
}