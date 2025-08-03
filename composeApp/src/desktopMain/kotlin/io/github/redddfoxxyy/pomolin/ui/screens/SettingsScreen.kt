package io.github.redddfoxxyy.pomolin.ui.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ScrollbarStyle
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.redddfoxxyy.pomolin.data.PomoDoro
import io.github.redddfoxxyy.pomolin.ui.ThemeManager
import org.jetbrains.compose.resources.painterResource
import pomolin.composeapp.generated.resources.Res
import pomolin.composeapp.generated.resources.back

@Composable
@Preview
internal fun SettingsScreen(pomoDoroManager: PomoDoro, onNavigateBack: () -> Unit) {
    val scrollState = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize().background(ThemeManager.colors.base)) {
        Box(Modifier.padding(top = 10.dp, bottom = 55.dp)) {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(end = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(5.dp))
                TimerOption(
                    option = "Focus Time",
                    timerValue = pomoDoroManager.appSettings.workingDuration,
                    onValueChange = { pomoDoroManager.changeWorkingDuration(it) }
                )
                Spacer(Modifier.height(5.dp))
                TimerOption(
                    option = "Short Break Time",
                    timerValue = pomoDoroManager.appSettings.shortBreakDuration,
                    onValueChange = { pomoDoroManager.changeShortBreakDuration(it) }
                )
                Spacer(Modifier.height(5.dp))
                TimerOption(
                    option = "Long Break Time",
                    timerValue = pomoDoroManager.appSettings.longBreakDuration,
                    onValueChange = { pomoDoroManager.changeLongBreakDuration(it) }
                )
                Spacer(Modifier.height(5.dp))
                workSessionOption(
                    option = "Pomodoro Sessions",
                    workSessionValue = pomoDoroManager.appSettings.workSessionDuration.toFloat(),
                    onValueChange = { pomoDoroManager.changeWorkSessionDuration(it) }
                )
                Spacer(Modifier.height(5.dp))
                toggleOption(
                    option = "Enable Progress Indicator",
                    optionValue = pomoDoroManager.appSettings.enableProgressIndicator,
                    onCheckedChange = { pomoDoroManager.appSettings.enableProgressIndicator = it }
                )
            }
            VerticalScrollbar(
                adapter = rememberScrollbarAdapter(scrollState),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxHeight()
                    .padding(horizontal = 8.dp),
                style = ScrollbarStyle(
                    thickness = 6.dp,
                    minimalHeight = 5.dp,
                    shape = RoundedCornerShape(3.dp),
                    hoverDurationMillis = 200,
                    unhoverColor = ThemeManager.colors.mauve.copy(alpha = 0.3f),
                    hoverColor = ThemeManager.colors.mauve
                )
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

@Composable
@Preview
fun toggleOption(option: String, optionValue: Boolean, onCheckedChange: (Boolean) -> Unit) {

    Row(
        modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)
            .padding(horizontal = 25.dp, vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$option:",
            color = ThemeManager.colors.lavender,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            modifier = Modifier.align(Alignment.CenterVertically),
        )

        Switch(
            checked = optionValue,
            onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedTrackColor = ThemeManager.colors.mauve,
                checkedThumbColor = ThemeManager.colors.base.copy(alpha = 0.9f),
                uncheckedTrackColor = ThemeManager.colors.surface200,
                uncheckedThumbColor = Color.White,
                uncheckedBorderColor = ThemeManager.colors.surface200,
                checkedBorderColor = ThemeManager.colors.mauve,
            )
        )
    }
}