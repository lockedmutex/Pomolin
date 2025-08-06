package io.github.redddfoxxyy.pomolin.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import io.github.redddfoxxyy.pomolin.data.AppSettings
import io.github.redddfoxxyy.pomolin.data.PomoDoro
import io.github.redddfoxxyy.pomolin.data.PomoDoroRoutines
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
        val progressIndicatorColor by animateColorAsState(
            targetValue = when (pomoDoroManager.currentRoutine) {
                PomoDoroRoutines.Working -> ThemeManager.colors.mauve
                PomoDoroRoutines.ShortBreak -> ThemeManager.colors.yellow
                PomoDoroRoutines.LongBreak -> ThemeManager.colors.peach
            },
            animationSpec = tween(durationMillis = 400),
            label = "ProgressColorAnimation"
        )

        if (squareSize > 0.dp) {
            Box(
                modifier = Modifier.size(squareSize),
                contentAlignment = Alignment.Center
            ) {
                if (AppSettings.enableProgressIndicator) {
                    val strokeWidth = squareSize / 25f

                    CircularProgressIndicator(
                        modifier = Modifier.fillMaxSize(),
                        color = progressIndicatorColor,
                        trackColor = progressIndicatorColor.copy(alpha = 0.3f),
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
                                fontWeight = if (AppSettings.enableProgressIndicator) FontWeight.Normal else FontWeight.ExtraBold,
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