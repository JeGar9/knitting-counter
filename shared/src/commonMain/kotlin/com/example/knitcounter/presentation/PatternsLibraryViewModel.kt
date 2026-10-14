package com.example.knitcounter.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knitcounter.application.PatternsService
import com.example.knitcounter.domain.Pattern
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class PatternLibraryUiState(
    val patterns: List<Pattern> = emptyList(),
    val searchQuery: String = "",
    val selectedTab: Int = -1,
    val isLoading: Boolean = false
)

class PatternsLibraryViewModel(val patternsService: PatternsService): ViewModel() {

    private val searchQuery = MutableStateFlow("")

    val uiState : StateFlow<PatternLibraryUiState> =
        combine(
            patternsService.getUserPatterns(),
            searchQuery
        ) { patterns, searchQuery ->
            PatternLibraryUiState(
                patterns = patterns.filter { it.name.contains(searchQuery, true) },
                searchQuery = searchQuery,
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            PatternLibraryUiState()
        )

    fun onPatternClick(pattern: Pattern) {

    }

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
    }

    fun onTabSelected(index: Int) {
        
    }
}