package io.github.redddfoxxyy.pomolin.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.redddfoxxyy.pomolin.data.PomoDoro
import io.github.redddfoxxyy.pomolin.data.PomoDoroRoutines
import io.github.redddfoxxyy.pomolin.ui.ThemeManager
import io.github.redddfoxxyy.pomolin.ui.components.TimerDisplay
import org.jetbrains.compose.resources.painterResource
import pomolin.composeapp.generated.resources.*

@Composable
@Preview
internal fun TimerScreen(pomoDoroManager: PomoDoro, onNavigateToSettings: () -> Unit) {

    val currentRoutine = pomoDoroManager.currentRoutine
    val isRunning = pomoDoroManager.timerInstance.isTimerRunning
    val formattedTime = pomoDoroManager.timerInstance.formatedTime
    val workSessionsCompleted = pomoDoroManager.workSessionsCompleted
    val workSessionDuration = pomoDoroManager.appSettings.workSessionDuration

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().background(ThemeManager.colors.base),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            RoutineSelector(
                modifier = Modifier.padding(
                    top = if (ThemeManager.enableWindowDecorations) {
                        15.dp
                    } else {
                        10.dp
                    }
                ),
                routines = pomoDoroManager.routineList,
                selectedRoutine = currentRoutine,
                onRoutineSelected = { pomoDoroManager.setRoutine(it) }
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(25.dp),
                contentAlignment = Alignment.Center
            ) {
                TimerDisplay(
                    modifier = Modifier
                        .widthIn(max = 450.dp)
                        .fillMaxSize(),
                    time = formattedTime,
                    pomoDoroManager = pomoDoroManager
                )
            }

            ControlButtons(
                modifier = Modifier.padding(bottom = 75.dp),
                isRunning = isRunning,
                onPlayPauseClick = {
                    if (isRunning) pomoDoroManager.pauseTimer() else pomoDoroManager.startTimer()
                },
                onResetClick = { pomoDoroManager.resetTimer() }
            )
        }
        IconButton(
            onClick = onNavigateToSettings,
            modifier = Modifier.align(Alignment.BottomStart).padding(5.dp)
        ) {
            Icon(
                painter = painterResource(Res.drawable.settings),
                contentDescription = "Settings",
                tint = ThemeManager.colors.mauve,
            )
        }
        Text(
            modifier = Modifier.align(Alignment.BottomEnd).padding(bottom = 18.dp, end = 15.dp),
            text = "$workSessionsCompleted/$workSessionDuration",
            color = ThemeManager.colors.mauve,
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
        )
    }
}

@Composable
fun RoutineSelector(
    modifier: Modifier = Modifier,
    routines: List<PomoDoroRoutines>,
    selectedRoutine: PomoDoroRoutines,
    onRoutineSelected: (PomoDoroRoutines) -> Unit
) {
    SingleChoiceSegmentedButtonRow(modifier = modifier) {
        routines.forEach { routine ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = routines.indexOf(routine),
                    count = routines.size
                ),
                onClick = { onRoutineSelected(routine) },
                selected = routine == selectedRoutine,
                label = { Text(routine.displayName) },
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = ThemeManager.colors.mauve,
                    inactiveContainerColor = Color.Transparent,
                    activeContentColor = ThemeManager.colors.crust,
                    inactiveContentColor = ThemeManager.colors.text
                ),
                border = BorderStroke(1.dp, ThemeManager.colors.mauve)
            )
        }
    }
}

@Composable
fun ControlButtons(
    modifier: Modifier = Modifier,
    isRunning: Boolean,
    onPlayPauseClick: () -> Unit,
    onResetClick: () -> Unit
) {
    val resetIconRotation = remember { mutableFloatStateOf(0f) }
    val animatedResetIconRotation by animateFloatAsState(
        targetValue = resetIconRotation.value,
        animationSpec = tween(durationMillis = 400),
        label = "ResetIconRotation"
    )
    val actionButtonColor by animateColorAsState(
        targetValue = if (isRunning) ThemeManager.colors.peach else ThemeManager.colors.green,
        label = "ButtonColor"
    )

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Button(
            onClick = onPlayPauseClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = actionButtonColor,
                contentColor = ThemeManager.colors.text
            ),
            modifier = Modifier.padding(5.dp)
        ) {
            AnimatedContent(
                targetState = isRunning,
                transitionSpec = { scaleIn() togetherWith scaleOut() },
                label = "ButtonIcon"
            ) { running ->
                Icon(
                    painter = if (running) painterResource(Res.drawable.pause)
                    else painterResource(Res.drawable.play_arrow),
                    contentDescription = if (running) "Pause" else "Play",
                    tint = ThemeManager.colors.crust
                )
            }
        }

        Spacer(Modifier.width(16.dp))

        Button(
            onClick = {
                onResetClick()
                resetIconRotation.value -= 360f
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = ThemeManager.colors.red,
                contentColor = ThemeManager.colors.text
            ),
            modifier = Modifier.padding(5.dp)
        ) {
            Icon(
                painter = painterResource(Res.drawable.reset),
                contentDescription = "Reset",
                tint = ThemeManager.colors.crust,
                modifier = Modifier.rotate(animatedResetIconRotation)
            )
        }
    }
}