package com.example.knitcounter.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.knitcounter.domain.Pattern

data class PatternCard (
    val pattern: Pattern,
    val onClick: () -> Unit
)

@Composable
fun PatternGrid(
    patterns: List<Pattern>,
    onPatternClick: (Pattern) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items (
            items = patterns,
            key = { it.id }
        ) { pattern ->
            PatternCard(
                pattern = pattern,
                onClick = { onPatternClick(pattern) }
            )
        }
    }
}