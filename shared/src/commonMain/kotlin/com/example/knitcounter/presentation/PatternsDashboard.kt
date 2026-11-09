package com.example.knitcounter.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.decodeToImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.knitcounter.domain.FileReference
import com.example.knitcounter.domain.Folder
import com.example.knitcounter.domain.Pattern
import com.example.knitcounter.domain.PatternCover
import knitcounter.shared.generated.resources.*
import kotlinx.coroutines.flow.MutableStateFlow
import org.jetbrains.compose.resources.painterResource
import java.time.Duration
import java.util.*

@Preview
@Composable
private fun PatternDashboardPreview()
{
    val state = MutableStateFlow(
        PatternLibraryUiState(
            visiblePatterns = listOf(
                PatternUiState(pattern =
                    Pattern(
                        id = 1,
                        ownerId = UUID.randomUUID(),
                        name = "pattern1",
                        gauge = mapOf(),
                        file = FileReference.None,
                        progression = 20,
                        cover = PatternCover.Icon(
                            Res.drawable.fabric_points.toString(),
                            background = Color.Magenta,
                            foreground = Color.Yellow
                        )
                    ),
                    cover = CoverUiState.NotLoaded
                ),
                PatternUiState(pattern =
                    Pattern(
                        id = 2,
                        ownerId = UUID.randomUUID(),
                        name = "pattern2",
                        gauge = mapOf(),
                        file = FileReference.None,
                        progression = 20,
                        cover = PatternCover.Icon(
                            Res.drawable.ball_without_needles.toString(),
                            background = Color.Black,
                            foreground = Color.White
                        )
                    ),
                    cover = CoverUiState.NotLoaded
                )
            )
        )
    )
    PatternDashboardContent(
        uiState = state.collectAsState().value,
        onPatternClick = {},
        onSearchQueryChanged = {},
        onCoverLoad = {},
        onTabSelected = {}
    )
}

@Composable
fun PatternDashboardScreen(
    viewModel: PatternsLibraryViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    PatternDashboardContent(
        uiState = uiState,
        onPatternClick = viewModel::onPatternClick,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onTabSelected = viewModel::onTabSelected,
        onCoverLoad = viewModel::loadCover
    )
}

private sealed interface DashboardItem {
    val name: String

    data class PatternItem(
        val pattern: Pattern,
    ) : DashboardItem {
        override val name = pattern.name
    }

    data class FolderItem(
        val folder: Folder
    ) : DashboardItem {
        override val name: String = folder.name
    }
}

@Composable
fun PatternDashboardContent(
    uiState: PatternLibraryUiState,
    onPatternClick: (PatternUiState) -> Unit,
    onCoverLoad: (PatternUiState) -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onTabSelected: (Int) -> Unit
) {
    MaterialTheme {
        Scaffold (
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.surface,
            topBar = {
                PatternDashboardHeader(uiState.statistics)
            },
            bottomBar = {
                BottonNavigationBar(
                    selectedTab = uiState.selectedTab,
                    onTabSelected = onTabSelected
                )
            }
        ){ innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                SearchField(uiState, onSearchQueryChanged)
                PatternGrid(uiState.visiblePatterns, onPatternClick, onCoverLoad)
            }
        }
    }
}


@Composable
fun PatternDashboardHeader(statisticsUiState: StatisticsUiState)
{
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .shadow(5.dp, RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            .background(color = MaterialTheme.colorScheme.secondary)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(8.dp))
        {
            val iconsColor = MaterialTheme.colorScheme.onSecondary

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Icon(Icons.Filled.Person, "person icon", tint = iconsColor)
                Icon(Icons.Filled.MoreHoriz, "person icon", tint = iconsColor)
                Icon(Icons.Filled.Notifications, "notification icon", tint = iconsColor)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Icon(painterResource(Res.drawable.ball_without_needles), tint = iconsColor, contentDescription = "Icon",
                    modifier = Modifier.size(24.dp))
                Icon(Icons.Filled.Settings, "settings icon", tint = iconsColor)
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth() .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatisticButton(Icons.Filled.Leaderboard, "Level ${statisticsUiState.level}", selected = false)
            StatisticButton(Res.drawable.needle_thread, "Stitches", value = statisticsUiState.totalStitches.toString(), selected = false)
            StatisticButton(Res.drawable.threads_on_needle, "Rows", value = statisticsUiState.totalRows.toString(), selected = false)
            StatisticButton(Res.drawable.pin_cushion, "Completed", value = statisticsUiState.completedPatterns.toString(), selected = false)
            StatisticButton(Icons.Filled.HourglassBottom, "Time", value = toMostRelevantTimes(statisticsUiState.totalTime), selected = false)
        }
    }
}

fun toMostRelevantTimes(totalTime: Duration) : String {

    val totalMinutes = totalTime.toMinutes();
    val dayPart = totalMinutes / 1440
    val hourPart = (totalMinutes % 1440) % 60
    val minutePart = totalMinutes % 60

    val parts = listOf(
        dayPart to "d",
        hourPart to "h",
        minutePart to "m",
    ).filter { it.first > 0 }.take(2)

    return parts.joinToString(" ") { (value, unit) -> "$value$unit"}.ifEmpty { "0m" }
}

@Composable
fun PatternGrid(
    patterns: List<PatternUiState>,
    onPatternClick: (PatternUiState) -> Unit,
    onCoverLoad: (PatternUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 90.dp),
        modifier = modifier.padding(16.dp).fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items (
            items = patterns,
            key = { it.id }
        ) { pattern ->
            PatternItem(
                pattern = pattern,
                onClick = onPatternClick,
                onCoverLoad = onCoverLoad
            )
        }
    }
}

@Composable
fun PatternItem(
    pattern: PatternUiState,
    onClick: (PatternUiState) -> Unit,
    onCoverLoad: (PatternUiState) -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().aspectRatio(1f)
        ) {
            when (val cover = pattern.cover) {
                is CoverUiState.NotLoaded -> NotLoadedPatternItem(pattern, onClick, onCoverLoad)
                is CoverUiState.Error -> ErrorPatternItem(pattern, onClick)
                is CoverUiState.LoadedIcon -> IconPatternItem(pattern, cover, onClick)
                is CoverUiState.LoadedImage -> ImagePatternItem(pattern, cover, onClick)
                is CoverUiState.Loading -> LoadingPatternItem(pattern, onClick)
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = pattern.pattern.name)
    }
}

@Composable
fun NotLoadedPatternItem(patternUi: PatternUiState,
                         onClick: (PatternUiState) -> Unit,
                         onCoverLoad: (PatternUiState) -> Unit) {
    Card(
        modifier = Modifier.fillMaxSize(),
        shape = CircleShape,
        onClick = { onClick(patternUi) }
    ) {
        onCoverLoad(patternUi)
    }
}

@Composable
fun ErrorPatternItem(patternUi: PatternUiState, onClick: (PatternUiState) -> Unit) {
    Card(
        modifier = Modifier.fillMaxSize(),
        shape = CircleShape,
        onClick = { onClick(patternUi) }
    ) {
        Icon(imageVector = Icons.Default.Error, contentDescription = "Image load error")
    }
}

@Composable
fun IconPatternItem(patternUi: PatternUiState,
                    cover: CoverUiState.LoadedIcon,
                    onClick: (PatternUiState) -> Unit) {
    Card(
        modifier = Modifier.fillMaxSize().background(cover.backgroundColor),
        shape = CircleShape,
        onClick = { onClick(patternUi) }
    ) {
        val drawableResource = Res.allDrawableResources[cover.resourceName]
        if (drawableResource == null) {
            Icon(imageVector = Icons.Default.Error, contentDescription = "Image load error")
        } else {
            Icon(painter = painterResource(drawableResource),
                contentDescription = cover.contentDescription,
                tint = cover.foregroundColor)
        }
    }
}

@Composable
fun ImagePatternItem(patternUi: PatternUiState,
                    cover: CoverUiState.LoadedImage,
                    onClick: (PatternUiState) -> Unit) {
    Card(
        modifier = Modifier.fillMaxSize(),
        shape = CircleShape,
        onClick = { onClick(patternUi) }
    ) {
        Image(bitmap = cover.bytes.decodeToImageBitmap(), contentDescription = cover.contentDescription)
    }
}

@Composable
fun LoadingPatternItem(patternUi: PatternUiState, onClick: (PatternUiState) -> Unit) {
    Card(
        modifier = Modifier.fillMaxSize(),
        shape = CircleShape,
        onClick = { onClick(patternUi) }
    ) {
        Box(modifier = Modifier.clip(CircleShape)
            .background(Color.LightGray.copy(alpha = 0.25f))) { }
    }
}
