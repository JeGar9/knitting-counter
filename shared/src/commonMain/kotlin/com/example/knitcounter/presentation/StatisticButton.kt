package com.example.knitcounter.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import javax.swing.text.IconView

@Composable
fun StatisticButton(
    icon: ImageVector,
    label: String,
    value: String? = null,
    selected: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(64.dp)
    ) {
        Box(
            modifier = Modifier
                .size(62.dp)
                .clip(CircleShape)
                .background(color = if (selected) colorScheme.secondary else MaterialTheme.colorScheme.surface)
                .border(1.dp, colorScheme.primary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, "contentDescription",
                tint = MaterialTheme.colorScheme.onBackground)

            if (value != null) {
                Text(
                    text = value,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))

        Text(
            label,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 11.sp,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
            maxLines = 2
        )

    }
}