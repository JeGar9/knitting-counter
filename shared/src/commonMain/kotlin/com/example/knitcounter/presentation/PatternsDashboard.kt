package com.example.knitcounter.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.knitcounter.domain.Folder
import com.example.knitcounter.domain.Pattern
import com.example.knitcounter.infrastructure.entities.PatternWithGauges
import kotlinx.coroutines.flow.MutableStateFlow

@Preview
@Composable
private fun PatternDashboardPreview()
{
    val state = MutableStateFlow(PatternLibraryUiState())
    PatternDashboardContent(
        uiState = state.collectAsState(),
        onPatternClick = {},
        onSearchQueryChanged = {},
        onTabSelected = {}
    )
}

@Composable
fun PatternDashboardScreen(
    viewModel: PatternsLibraryViewModel
) {
    val uiState = viewModel.uiState.collectAsState()
    PatternDashboardContent(
        uiState = uiState,
        onPatternClick = viewModel::onPatternClick,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onTabSelected = viewModel::onTabSelected
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
    uiState: State<PatternLibraryUiState>,
    onPatternClick: (Pattern) -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onTabSelected: (Int) -> Unit
) {
    MaterialTheme {
        var patterns by remember { mutableStateOf<List<PatternWithGauges>>(emptyList()) }
        var query by remember { mutableStateOf("") }
        val entries = uiState.value.patterns
        val filteredEntries = entries.filter {
            x -> query.isEmpty() || x.name.contains(query, true)
        }

        Scaffold (
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            topBar = { TopAppBar(
                title = { Text("Patterns") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                )
            ) },
            bottomBar = {
                BottonNavigationBar(
                    selectedTab = uiState.value.selectedTab,
                    onTabSelected = onTabSelected
                )
            }
        ){ innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    placeholder = { Text("Search...") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    },
                    singleLine = true
                )
                LazyColumn (
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = filteredEntries,
                        key = { it.id }
                    ) { entry ->
                        Card(
                            modifier = Modifier.fillMaxWidth().clickable {

                            }
                        ) {
                            Column (
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = entry.name,
                                    style = MaterialTheme.typography.titleMedium,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}