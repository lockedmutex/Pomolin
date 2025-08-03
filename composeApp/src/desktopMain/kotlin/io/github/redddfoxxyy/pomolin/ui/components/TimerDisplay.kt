package io.github.redddfoxxyy.pomolin.ui.components

import androidx.compose.animation.AnimatedContent
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.redddfoxxyy.pomolin.data.PomoDoro
import io.github.redddfoxxyy.pomolin.ui.ThemeManager

@Composable
@Preview
internal fun TimerDisplay(time: String, pomoDoroManager: PomoDoro) {
    if (pomoDoroManager.appSettings.enableProgressIndicator) {
        Box(
            modifier = Modifier.size(250.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(250.dp),
                color = ThemeManager.colors.mauve,
                trackColor = ThemeManager.colors.mauve.copy(alpha = 0.3f),
                strokeWidth = 10.dp,
                progress = { pomoDoroManager.timerInstance.getTimerProgress() }
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
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
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 70.sp,
                            color = ThemeManager.colors.lavender
                        )
                    }
                }
            }
        }
    } else {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
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
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 100.sp,
                        color = ThemeManager.colors.lavender
                    )
                }
            }
        }
    }

}