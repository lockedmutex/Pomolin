// Timer.kt
package com.redddfoxxyy.pomolin.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.redddfoxxyy.pomolin.handlers.PomoDoro
import com.redddfoxxyy.pomolin.handlers.PomoDoroRoutines
import com.redddfoxxyy.pomolin.ui.ThemeManager
import org.jetbrains.compose.resources.painterResource
import pomolin.composeapp.generated.resources.*

@Composable
@Preview
internal fun TimerScreen(pomoDoroManager: PomoDoro, onNavigateToSettings: () -> Unit) {

	val currentRoutine = pomoDoroManager.currentRoutine
	val isRunning = pomoDoroManager.currentTimer.isTimerRunning.collectAsState()
	val formattedTime = pomoDoroManager.currentTimer.formatedTime.value

	Box(modifier = Modifier.fillMaxSize()) {
		Column(
			Modifier.fillMaxSize().background(ThemeManager.colors.base),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Spacer(Modifier.height(15.dp))

			RoutineSelector(
				routines = pomoDoroManager.routineList,
				selectedRoutine = currentRoutine,
				onRoutineSelected = { pomoDoroManager.setRoutine(it) }
			)

			Spacer(Modifier.height(75.dp))

			TimerDisplay(time = formattedTime)

			Spacer(Modifier.height(60.dp))

			ControlButtons(
				isRunning = isRunning.value,
				onPlayPauseClick = {
					if (isRunning.value) pomoDoroManager.pauseTimer() else pomoDoroManager.startTimer()
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
	}
}

@Composable
fun RoutineSelector(
	routines: List<PomoDoroRoutines>,
	selectedRoutine: PomoDoroRoutines,
	onRoutineSelected: (PomoDoroRoutines) -> Unit
) {
	SingleChoiceSegmentedButtonRow {
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
				border = BorderStroke(1.dp, ThemeManager.colors.mauve),
			)
		}
	}
}

@Composable
fun TimerDisplay(time: String) {
	Row(
		horizontalArrangement = Arrangement.Center,
		verticalAlignment = Alignment.CenterVertically
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

@Composable
fun ControlButtons(
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
		modifier = Modifier.fillMaxWidth()
	) {
		// Play/Pause(Action) Button
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

		// Reset Button
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