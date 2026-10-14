package com.example.knitcounter.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.Duration

@Composable
fun PatternDashboardHeader(
    level: Int,
    totalStitches: Int,
    totalRows: Int,
    completedPatterns: Int,
    totalTime: Duration
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(20.dp))
        {
            Icon(Icons.Filled.Person, "person icon", tint = Color.White)
            Icon(Icons.Filled.MoreHoriz, "person icon", tint = Color.White)
            Icon(Icons.Filled.Notifications, "notification icon", tint = Color.Yellow)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            Text("+", color = Color.White, fontSize = 25.sp)
            Icon(Icons.Filled.Settings, "settings icon", tint = Color.White)
        }
    }

    Spacer(Modifier.height(30.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        StatisticButton(Icons.Filled.Leaderboard, "Level $level", selected = true)
        StatisticButton(Icons.Outlined.Needle)
    }
}