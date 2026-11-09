package com.example.knitcounter.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import knitcounter.shared.generated.resources.Res
import knitcounter.shared.generated.resources.needle_thread
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import javax.swing.text.IconView
import kotlin.math.sqrt

@Preview
@Composable
fun StatisticButtonPreview() =
    StatisticButton(Res.drawable.needle_thread, "Stitches", value = "100", selected = false)


@Composable
fun StatisticButton(
    painter: Painter,
    label: String,
    value: String? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(64.dp)
    ) {
        BoxWithConstraints (
            modifier = Modifier
                .size(62.dp)
                .clip(CircleShape)
                .shadow(5.dp, CircleShape)
                .background(color = colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            val maxIconSize = minOf(maxWidth, maxHeight) / sqrt(2f)

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth().fillMaxHeight().clip(CircleShape).padding(4.dp))
            {
                Icon(painter, "$label: $value",
                    tint = colorScheme.onSecondaryContainer,
                    modifier = Modifier.size(maxIconSize * if (value != null) 0.75f else 1f))

                if (value != null) {
                    Text(
                        text = value,
                        fontSize = 11.sp,
                        color = colorScheme.onSecondaryContainer,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(5.dp))

        Text(
            label,
            color = colorScheme.onSecondary,
            fontSize = 11.sp,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
            maxLines = 2
        )

    }
}


@Composable
fun StatisticButton(iconVector: ImageVector,
                    label: String,
                    value: String? = null,
                    selected: Boolean = false
) = StatisticButton(rememberVectorPainter(iconVector), label, value)

@Composable
fun StatisticButton(drawable: DrawableResource,
                    label: String,
                    value: String? = null,
                    selected: Boolean = false
) = StatisticButton(painterResource(drawable), label, value)
