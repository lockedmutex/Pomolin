package io.github.redddfoxxyy.pomolin

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.github.redddfoxxyy.pomolin.data.PomoDoro
import io.github.redddfoxxyy.pomolin.ui.screens.SettingsScreen
import io.github.redddfoxxyy.pomolin.ui.screens.TimerScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class Screen {
    Timer, Settings
}

@Composable
@Preview
fun App() {
    var currentScreen by remember { mutableStateOf(Screen.Timer) }
    val pomoDoroManager = remember { PomoDoro() }

    MaterialTheme {
        Scaffold(modifier = Modifier.safeContentPadding().fillMaxSize()) {
            AnimatedContent(
                targetState = currentScreen,
                transitionSpec = {
                    if (targetState == Screen.Settings) {
                        slideInHorizontally(
                            animationSpec = tween(300),
                            initialOffsetX = { fullWidth -> -fullWidth }
                        ) togetherWith
                                slideOutHorizontally(
                                    animationSpec = tween(300),
                                    targetOffsetX = { fullWidth -> fullWidth }
                                )
                    } else {
                        slideInHorizontally(
                            animationSpec = tween(300),
                            initialOffsetX = { fullWidth -> fullWidth }
                        ) togetherWith
                                slideOutHorizontally(
                                    animationSpec = tween(300),
                                    targetOffsetX = { fullWidth -> -fullWidth }
                                )
                    }
                },
                label = "Screen Transition"
            ) { screen ->
                when (screen) {
                    Screen.Timer -> TimerScreen(
                        pomoDoroManager = pomoDoroManager,
                        onNavigateToSettings = { currentScreen = Screen.Settings }
                    )

                    Screen.Settings -> SettingsScreen(
                        pomoDoroManager = pomoDoroManager,
                        onNavigateBack = { currentScreen = Screen.Timer }
                    )
                }
            }
        }
    }
}
