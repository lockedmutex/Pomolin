package io.github.redddfoxxyy.pomolin.ui.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Restart Required") },
            text = { Text("Please restart the application for the changes to take effect.") },
            confirmButton = {
                Button(
                    onClick = { showDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ThemeManager.colors.mauve,
                        contentColor = ThemeManager.colors.crust
                    )
                ) {
                    Text("OK")
                }
            },
            containerColor = ThemeManager.colors.surface,
            titleContentColor = ThemeManager.colors.lavender,
            textContentColor = ThemeManager.colors.text
        )
    }

    Box(modifier = Modifier.fillMaxSize().background(ThemeManager.colors.base)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 55.dp)
        ) {
            Text(
                text = "Settings",
                color = ThemeManager.colors.lavender,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 22.dp, top = 20.dp, bottom = 10.dp)
            )

            // Scrollable content
            Box(modifier = Modifier.weight(1f)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(horizontal = 18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    // Timer Settings
                    SettingItemCard(title = "Timer Durations") {
                        TimerOption(
                            label = "Focus",
                            value = pomoDoroManager.appSettings.workingDuration,
                            onValueChange = { pomoDoroManager.changeWorkingDuration(it) },
                            valueSuffix = "min",
                            valueRange = 1f..120f,
                            // icon = painterResource(Res.drawable.ic_brain)
                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            thickness = 1.dp,
                            color = ThemeManager.colors.surface200.copy(alpha = 0.5f)
                        )
                        TimerOption(
                            label = "Short Break",
                            value = pomoDoroManager.appSettings.shortBreakDuration,
                            onValueChange = { pomoDoroManager.changeShortBreakDuration(it) },
                            valueSuffix = "min",
                            valueRange = 1f..30f,
                            // icon = painterResource(Res.drawable.ic_coffee)
                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            thickness = 1.dp,
                            color = ThemeManager.colors.surface200.copy(alpha = 0.5f)
                        )
                        TimerOption(
                            label = "Long Break",
                            value = pomoDoroManager.appSettings.longBreakDuration,
                            onValueChange = { pomoDoroManager.changeLongBreakDuration(it) },
                            valueSuffix = "min",
                            valueRange = 1f..60f,
                            // icon = painterResource(Res.drawable.ic_long_coffee)
                        )
                    }

                    // Session Settings
                    SettingItemCard(title = "Session Goal") {
                        TimerOption(
                            label = "Sessions",
                            value = pomoDoroManager.appSettings.workSessionDuration.toFloat(),
                            onValueChange = { pomoDoroManager.changeWorkSessionDuration(it) },
                            valueSuffix = "sessions",
                            valueRange = 1f..10f,
                            // icon = painterResource(Res.drawable.ic_target)
                        )
                    }

                    // Appearance Settings
                    SettingItemCard(title = "Appearance") {
                        ToggleOption(
                            label = "Progress Indicator",
                            isChecked = pomoDoroManager.appSettings.enableProgressIndicator,
                            onCheckedChange = {
                                pomoDoroManager.appSettings.enableProgressIndicator = it
                            },
                            // icon = painterResource(Res.drawable.ic_progress) // Example icon
                        )

                        ToggleOption(
                            label = "Enable Window Decorations",
                            isChecked = ThemeManager.enableWindowDecorations,
                            onCheckedChange = {
                                // ThemeManager.enableWindowDecorations = it
                                ThemeManager.toggleWindowDecorations()
                                showDialog = true
                            },
                            // icon = painterResource(Res.drawable.ic_progress) // Example icon
                        )

                        ToggleOption(
                            label = "Enable Window Borders",
                            isChecked = ThemeManager.enableWindowBorders,
                            onCheckedChange = {
                                ThemeManager.enableWindowBorders = it
                                ThemeManager.saveSettings()
                            },
                            enabled = !ThemeManager.enableWindowDecorations,
                            // icon = painterResource(Res.drawable.ic_progress) // Example icon
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                }

                VerticalScrollbar(
                    adapter = rememberScrollbarAdapter(scrollState),
                    modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight()
                        .padding(end = 6.dp),
                    style = ScrollbarStyle(
                        thickness = 6.dp,
                        shape = RoundedCornerShape(3.dp),
                        unhoverColor = ThemeManager.colors.mauve.copy(alpha = 0.3f),
                        hoverColor = ThemeManager.colors.mauve,
                        hoverDurationMillis = 300,
                        minimalHeight = 100.dp,
                    )
                )
            }
        }

        IconButton(
            onClick = onNavigateBack,
            modifier = Modifier.align(Alignment.BottomEnd).padding(5.dp)
        ) {
            Icon(
                painter = painterResource(Res.drawable.back),
                contentDescription = "Back to Timer",
                tint = ThemeManager.colors.mauve,
            )
        }
    }
}

@Composable
fun SettingItemCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        color = ThemeManager.colors.surface,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Text(
                text = title,
                color = ThemeManager.colors.lavender,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 4.dp)
            )
            content()
        }
    }
}


@Composable
fun TimerOption(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueSuffix: String,
    valueRange: ClosedFloatingPointRange<Float>,
    // icon: Painter,
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Icon(painter = icon, contentDescription = label, tint = ThemeManager.colors.mauve, modifier = Modifier.size(20.dp))
            // Spacer(Modifier.width(12.dp))
            Text(
                text = label,
                color = ThemeManager.colors.text,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = "${value.toInt()} $valueSuffix",
                color = ThemeManager.colors.lavender,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
            )
        }
        Spacer(Modifier.height(4.dp))
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            colors = SliderDefaults.colors(
                thumbColor = ThemeManager.colors.mauve,
                activeTrackColor = ThemeManager.colors.mauve,
                inactiveTrackColor = ThemeManager.colors.mauve.copy(alpha = 0.3f),
            )
        )
    }
}

@Composable
fun ToggleOption(
    label: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true,
    // icon: Painter,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Icon(painter = icon, contentDescription = label, tint = ThemeManager.colors.mauve, modifier = Modifier.size(20.dp))
            // Spacer(Modifier.width(12.dp))
            Text(
                text = label,
                color = if (enabled) {
                    ThemeManager.colors.text
                } else {
                    ThemeManager.colors.text.copy(alpha = 0.7f)
                },
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
            )
        }
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            colors = SwitchDefaults.colors(
                checkedTrackColor = ThemeManager.colors.mauve,
                checkedThumbColor = ThemeManager.colors.base,
                uncheckedTrackColor = ThemeManager.colors.surface200,
                uncheckedThumbColor = ThemeManager.colors.crust,
                uncheckedBorderColor = ThemeManager.colors.surface200.copy(alpha = 0.0f),
                disabledUncheckedTrackColor = ThemeManager.colors.surface200.copy(alpha = 0.7f),
                disabledUncheckedThumbColor = ThemeManager.colors.crust.copy(alpha = 0.7f),
                disabledUncheckedBorderColor = ThemeManager.colors.surface200.copy(alpha = 0.2f),
            )
        )
    }
}