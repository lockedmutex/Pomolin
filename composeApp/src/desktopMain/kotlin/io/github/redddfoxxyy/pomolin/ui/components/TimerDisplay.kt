package io.github.redddfoxxyy.pomolin.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import io.github.redddfoxxyy.pomolin.data.PomoDoro
import io.github.redddfoxxyy.pomolin.ui.ThemeManager
import org.jetbrains.compose.resources.Font
import pomolin.composeapp.generated.resources.JetBrainsMonoNerdFont_ExtraBold
import pomolin.composeapp.generated.resources.Res

@Composable
@Preview
internal fun TimerDisplay(
    modifier: Modifier = Modifier,
    time: String,
    pomoDoroManager: PomoDoro
) {
    val timerFontFamily = FontFamily(Font(Res.font.JetBrainsMonoNerdFont_ExtraBold))
    val animatedProgress by animateFloatAsState(
        targetValue = pomoDoroManager.timerInstance.getTimerProgress(),
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearEasing
        ),
        label = "ProgressAnimation"
    )

    var size by remember { mutableStateOf(IntSize.Zero) }
    val density = LocalDensity.current

    Box(
        modifier = modifier.onSizeChanged { size = it },
        contentAlignment = Alignment.Center
    ) {
        val squareSize = with(density) { min(size.width.toDp(), size.height.toDp()) }

        if (squareSize > 0.dp) {
            Box(
                modifier = Modifier.size(squareSize),
                contentAlignment = Alignment.Center
            ) {
                if (pomoDoroManager.appSettings.enableProgressIndicator) {
                    val strokeWidth = squareSize / 25f

                    CircularProgressIndicator(
                        modifier = Modifier.fillMaxSize(),
                        color = ThemeManager.colors.mauve,
                        trackColor = ThemeManager.colors.mauve.copy(alpha = 0.3f),
                        strokeWidth = strokeWidth,
                        progress = { animatedProgress }
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
//                    val fontSize = if (pomoDoroManager.appSettings.enableProgressIndicator) {
//                        (squareSize.value / 3.5f).sp
//                    } else {
//                        (squareSize.value / 2.5f).sp
//                    }
                    val fontSize = (squareSize.value / 3.5f).sp

                    time.forEach { char ->
                        AnimatedContent(
                            targetState = char,
                            transitionSpec = {
                                slideInVertically { height -> height } togetherWith
                                        slideOutVertically { height -> -height }
                            }
                        ) { targetChar ->
                            Text(
                                text = targetChar.toString(),
                                fontWeight = if (pomoDoroManager.appSettings.enableProgressIndicator) FontWeight.Normal else FontWeight.ExtraBold,
                                fontSize = fontSize,
                                color = ThemeManager.colors.lavender,
                                fontFamily = timerFontFamily
                            )
                        }
                    }
                }
            }
        }
    }
}