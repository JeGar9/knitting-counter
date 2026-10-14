package com.example.knitcounter.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun BottonNavigationBar (
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
){
    val icons = listOf(
        Icons.Outlined.Settings,
        Icons.Outlined.Groups,
        Icons.Outlined.Folder,
        Icons.Outlined.PersonOutline
    )

    Row (
        modifier = Modifier.fillMaxWidth()
            .height(68.dp)
            .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp))
            .background(MaterialTheme.colorScheme.background)
            .border(1.dp, MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)),
        horizontalArrangement =  Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        icons.forEachIndexed { index, icon ->
            Icon(
                imageVector = icon,
                contentDescription = "Navigation tab ${index + 1}",
                tint = if (selectedTab == index) MaterialTheme.colorScheme.onBackground
                    else MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(30.dp).clickable { onTabSelected(index) }
            )
        }
    }
}