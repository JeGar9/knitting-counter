package com.example.knitcounter.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knitcounter.application.PatternsService
import com.example.knitcounter.domain.Pattern
import com.example.knitcounter.domain.PatternCover
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import java.net.URI
import java.time.Duration

data class StatisticsUiState(
    val level: Int = 0,
    val totalStitches: Int = 0,
    val totalRows: Int = 0,
    val completedPatterns: Int = 0,
    val totalTime: Duration = Duration.ZERO
)

sealed interface CoverUiState {
    object Loading : CoverUiState
    object Error : CoverUiState
    object NotLoaded : CoverUiState
    class LoadedImage(val bytes: ByteArray, val contentDescription: String = "") : CoverUiState
    class LoadedIcon(val resourceName: String,
                     val backgroundColor: Color,
                     val foregroundColor: Color,
                     val contentDescription: String) : CoverUiState
}

data class PatternUiState(
    val pattern: Pattern,
    var cover: CoverUiState
) {
    val id = pattern.id
}

data class PatternLibraryUiState(
    val currentPath: URI = URI.create(""),
    val visiblePatterns: List<PatternUiState> = emptyList(),
    val searchQuery: String = "",
    val selectedTab: Int = -1,
    val isLoading: Boolean = false,
    val statistics: StatisticsUiState = StatisticsUiState(),
)

class PatternsLibraryViewModel(val patternsService: PatternsService): ViewModel()
{
    private val coverLoadingLock = Any()
    private val searchQuery = MutableStateFlow("")
    private val coverStates = MutableStateFlow<Map<Long, CoverUiState>>(emptyMap())

    val uiState : StateFlow<PatternLibraryUiState> =
        combine(
            patternsService.getUserPatterns(),
            searchQuery,
            coverStates,
        ) { patterns, searchQuery, coverStates ->
            PatternLibraryUiState(
                visiblePatterns = patterns.filter {
                    it.name.contains(searchQuery, true)
                }.map {
                    PatternUiState(pattern = it, cover = when(val coverImage = it.cover) {
                        is PatternCover.None -> CoverUiState.Error
                        is PatternCover.Icon -> CoverUiState.LoadedIcon(coverImage.name,
                            coverImage.background, coverImage.foreground,
                            contentDescription = "")
                        is PatternCover.Image -> coverStates.getOrDefault(it.id, CoverUiState.NotLoaded)
                    })
                },
                searchQuery = searchQuery,
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            PatternLibraryUiState()
        )

    fun loadCover(patternUi: PatternUiState) {
        val cover = patternUi.pattern.cover
        if (cover is PatternCover.Image) {
            loadCover(patternUi.id, cover)
        }
    }

    fun loadCover(patternId: Long, coverImageData: PatternCover.Image) {
        val shouldLoad = synchronized(coverLoadingLock) {
            if (coverStates.value.containsKey(patternId)) {
                false
            } else {
                coverStates.update {
                    it + (patternId to CoverUiState.Loading)
                }
                true
            }
        }

        if (!shouldLoad)
            return

        viewModelScope.launch {
            try {
                val bytes = patternsService.getCoverImage(coverImageData)

                coverStates.update {
                    it + (patternId to CoverUiState.LoadedImage(bytes))
                }

            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                coverStates.update {
                    it + (patternId to CoverUiState.Loading)
                }
            }
        }
    }

    fun onPatternClick(pattern: PatternUiState) {

    }

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
    }

    fun onTabSelected(index: Int) {
        
    }
}