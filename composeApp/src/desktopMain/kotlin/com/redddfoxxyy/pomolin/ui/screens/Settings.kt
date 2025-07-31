package com.redddfoxxyy.pomolin.ui.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.redddfoxxyy.pomolin.handlers.PomoDoro
import com.redddfoxxyy.pomolin.ui.ThemeManager
import org.jetbrains.compose.resources.painterResource
import pomolin.composeapp.generated.resources.Res
import pomolin.composeapp.generated.resources.back

@Composable
@Preview
internal fun SettingsScreen(pomoDoroManager: PomoDoro, onNavigateBack: () -> Unit) {
	Box(modifier = Modifier.fillMaxSize()) {
		Column(
			Modifier.fillMaxSize().background(ThemeManager.colors.base),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Spacer(Modifier.height(15.dp))
			TimerOption(
				option = "Focus Time",
				timerValue = pomoDoroManager.routineSettings.workingDuration,
				onValueChange = { pomoDoroManager.changeWorkingDuration(it) }
			)
			Spacer(Modifier.height(5.dp))
			TimerOption(
				option = "Short Break Time",
				timerValue = pomoDoroManager.routineSettings.shortBreakDuration,
				onValueChange = { pomoDoroManager.changeShortBreakDuration(it) }
			)
			Spacer(Modifier.height(5.dp))
			TimerOption(
				option = "Long Break Time",
				timerValue = pomoDoroManager.routineSettings.longBreakDuration,
				onValueChange = { pomoDoroManager.changeLongBreakDuration(it) }
			)
			Spacer(Modifier.height(5.dp))
			workSessionOption(
				option = "Pomodoro Sessions",
				workSessionValue = pomoDoroManager.routineSettings.workSessionDuration.toFloat(),
				onValueChange = { pomoDoroManager.changeWorkSessionDuration(it) }
			)
		}

		IconButton(
			onClick = onNavigateBack,
			modifier = Modifier.align(Alignment.BottomEnd).padding(5.dp)
		) {
			Icon(
				painter = painterResource(Res.drawable.back),
				contentDescription = "Settings",
				tint = ThemeManager.colors.mauve,
			)
		}
	}
}

@Composable
@Preview
fun TimerOption(option: String, timerValue: Float, onValueChange: (Float) -> Unit) {

	Column(
		modifier = Modifier.height(IntrinsicSize.Min).padding(horizontal = 25.dp, vertical = 5.dp)
	) {
		Text(
			text = option,
			color = ThemeManager.colors.lavender,
			fontWeight = FontWeight.Bold,
			fontSize = 16.sp,
		)

		Spacer(Modifier.height(5.dp))

		Slider(
			value = timerValue,
			onValueChange,
			valueRange = 1f..120f,
			colors = SliderDefaults.colors(
				thumbColor = ThemeManager.colors.mauve,
				activeTrackColor = ThemeManager.colors.mauve,
				inactiveTrackColor = ThemeManager.colors.mauve.copy(alpha = 0.3f),
			)
		)

		Spacer(Modifier.height(3.dp))

		Text(
			text = (timerValue.toInt()).toString() + " Minutes",
			color = ThemeManager.colors.lavender,
			fontWeight = FontWeight.SemiBold,
			fontSize = 14.sp,
		)
	}
}

@Composable
@Preview
fun workSessionOption(option: String, workSessionValue: Float, onValueChange: (Float) -> Unit) {

	Column(
		modifier = Modifier.height(IntrinsicSize.Min).padding(horizontal = 25.dp, vertical = 5.dp)
	) {
		Text(
			text = option,
			color = ThemeManager.colors.lavender,
			fontWeight = FontWeight.Bold,
			fontSize = 16.sp,
		)

		Spacer(Modifier.height(5.dp))

		Slider(
			value = workSessionValue,
			onValueChange,
			valueRange = 1f..10f,
			colors = SliderDefaults.colors(
				thumbColor = ThemeManager.colors.mauve,
				activeTrackColor = ThemeManager.colors.mauve,
				inactiveTrackColor = ThemeManager.colors.mauve.copy(alpha = 0.3f),
			)
		)

		Spacer(Modifier.height(3.dp))

		Text(
			text = (workSessionValue.toInt()).toString() + " Sessions",
			color = ThemeManager.colors.lavender,
			fontWeight = FontWeight.SemiBold,
			fontSize = 14.sp,
		)
	}
}