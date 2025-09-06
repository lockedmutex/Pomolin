package io.github.redddfoxxyy.pomolin.ui.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.redddfoxxyy.pomolin.data.AppSettings
import io.github.redddfoxxyy.pomolin.data.PomoDoro
import io.github.redddfoxxyy.pomolin.data.ThemeMode
import io.github.redddfoxxyy.pomolin.ui.ThemeManager
import org.apache.commons.lang3.SystemUtils
import org.jetbrains.compose.resources.painterResource
import pomolin.composeapp.generated.resources.Res
import pomolin.composeapp.generated.resources.arrow_drop_down
import pomolin.composeapp.generated.resources.back

@Composable
@Preview
internal fun SettingsScreen(onNavigateBack: () -> Unit, restartWindow: () -> Unit = {}) {
	val scrollState = rememberScrollState()

	Box(modifier = Modifier.fillMaxSize().background(ThemeManager.colors.background)) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(bottom = 55.dp)
		) {
			Text(
				text = "Settings",
				color = ThemeManager.colors.boldText,
				fontSize = 24.sp,
				fontWeight = FontWeight.Bold,
				modifier = Modifier.padding(start = 18.dp, top = 15.dp, bottom = 10.dp)
			)

			Box(modifier = Modifier.weight(1f)) {
				Column(
					modifier = Modifier
						.fillMaxSize()
						.verticalScroll(scrollState)
						.padding(start = 14.dp, end = 20.dp),
					verticalArrangement = Arrangement.spacedBy(12.dp),
				) {
					// Timer Settings
					SettingItemCard(title = "Timer Durations") {
						TimerOption(
							label = "Focus",
							value = PomoDoro.pomoDoroSettings.workingDuration,
							onValueChange = { PomoDoro.changeWorkingDuration(it) },
							valueSuffix = "min",
							valueRange = 1f..120f,
							color = ThemeManager.colors.primaryAccent,
							// icon = painterResource(Res.drawable.ic_brain)
						)
						HorizontalDivider(
							modifier = Modifier.padding(horizontal = 16.dp),
							thickness = 1.dp,
							color = ThemeManager.colors.surface200.copy(alpha = 0.5f)
						)
						TimerOption(
							label = "Short Break",
							value = PomoDoro.pomoDoroSettings.shortBreakDuration,
							onValueChange = { PomoDoro.changeShortBreakDuration(it) },
							valueSuffix = "min",
							valueRange = 1f..60f,
							color = ThemeManager.colors.primaryBreak,
							// icon = painterResource(Res.drawable.ic_coffee)
						)
						HorizontalDivider(
							modifier = Modifier.padding(horizontal = 16.dp),
							thickness = 1.dp,
							color = ThemeManager.colors.surface200.copy(alpha = 0.5f)
						)
						TimerOption(
							label = "Long Break",
							value = PomoDoro.pomoDoroSettings.longBreakDuration,
							onValueChange = { PomoDoro.changeLongBreakDuration(it) },
							valueSuffix = "min",
							valueRange = 1f..120f,
							color = ThemeManager.colors.primaryLBreak,
							// icon = painterResource(Res.drawable.ic_long_coffee)
						)
					}

					// Session Settings
					SettingItemCard(title = "Session Goal") {
						TimerOption(
							label = "Sessions",
							value = PomoDoro.pomoDoroSettings.workSessionDuration.toFloat(),
							onValueChange = { PomoDoro.changeWorkSessionDuration(it) },
							valueSuffix = "sessions",
							valueRange = 1f..10f,
							color = ThemeManager.colors.blue,
							// icon = painterResource(Res.drawable.ic_target)
						)
					}

					// Appearance Settings
					SettingItemCard(title = "Appearance") {
						var themeExpanded by remember { mutableStateOf(false) }
						Row(
							modifier = Modifier
								.fillMaxWidth()
								.padding(horizontal = 16.dp, vertical = 12.dp),
							verticalAlignment = Alignment.CenterVertically,
							horizontalArrangement = Arrangement.SpaceBetween
						) {
							Text(
								text = "Theme",
								color = ThemeManager.colors.text,
								fontWeight = FontWeight.Medium,
								fontSize = 16.sp
							)
							Box {
								Surface(
									shape = RoundedCornerShape(8.dp),
									border = BorderStroke(1.dp, ThemeManager.colors.primaryAccent),
									color = Color.Transparent,
									modifier = Modifier.clickable { themeExpanded = true }
								) {
									Row(
										verticalAlignment = Alignment.CenterVertically,
										modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
									) {
										Text(
											text = AppSettings.themeMode.name,
											color = ThemeManager.colors.boldText,
											fontWeight = FontWeight.SemiBold,
											fontSize = 14.sp,
										)
										Spacer(Modifier.width(8.dp))
										Icon(
											painter = painterResource(Res.drawable.arrow_drop_down),
											contentDescription = "Select Theme",
											tint = ThemeManager.colors.primaryAccent
										)
									}
								}
								DropdownMenu(
									expanded = themeExpanded,
									onDismissRequest = { themeExpanded = false },
									modifier = Modifier.background(ThemeManager.colors.surface)
								) {
									DropdownMenuItem(
										text = { Text("Light", color = ThemeManager.colors.text) },
										onClick = {
											AppSettings.setTheme(ThemeMode.Light)
											themeExpanded = false
										}
									)
									DropdownMenuItem(
										text = { Text("Dark", color = ThemeManager.colors.text) },
										onClick = {
											AppSettings.setTheme(ThemeMode.Dark)
											themeExpanded = false
										}
									)
									if (!SystemUtils.IS_OS_WINDOWS) {
										DropdownMenuItem(
											text = { Text("Automatic", color = ThemeManager.colors.text) },
											onClick = {
												AppSettings.setTheme(ThemeMode.Automatic)
												themeExpanded = false
											}
										)
									}
								}
							}
						}
						ToggleOption(
							label = "Progress Indicator",
							isChecked = AppSettings.enableProgressIndicator,
							onCheckedChange = {
								AppSettings.toggleProgressIndicator(it)
							},
						)

						ToggleOption(
							label = "Enable Window Decorations",
							isChecked = AppSettings.enableWindowDecorations,
							onCheckedChange = {
								AppSettings.toggleWindowDecorations(it)
								restartWindow()
							},
						)

						ToggleOption(
							label = "Enable Window Borders",
							isChecked = AppSettings.enableWindowBorders,
							onCheckedChange = {
								AppSettings.toggleWindowBorders(it)
							},
							enabled = !AppSettings.enableWindowDecorations,
						)
					}
					Spacer(Modifier.height(16.dp))
				}

				VerticalScrollbar(
					adapter = rememberScrollbarAdapter(scrollState),
					modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight()
						.padding(
							end = if (AppSettings.enableWindowDecorations) {
								7.dp
							} else {
								8.dp
							}
						),
					style = ScrollbarStyle(
						thickness = 6.dp,
						shape = RoundedCornerShape(3.dp),
						unhoverColor = ThemeManager.colors.primaryAccent.copy(alpha = 0.3f),
						hoverColor = ThemeManager.colors.primaryAccent,
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
				tint = ThemeManager.colors.primaryAccent,
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
				color = ThemeManager.colors.boldText,
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
	color: Color,
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
				color = ThemeManager.colors.boldText,
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
				thumbColor = color,
				activeTrackColor = color,
				inactiveTrackColor = color.copy(alpha = 0.3f),
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
				checkedTrackColor = ThemeManager.colors.primaryAccent,
				checkedThumbColor = ThemeManager.colors.background.copy(alpha = 0.9f),
				uncheckedTrackColor = ThemeManager.colors.surface200,
				uncheckedThumbColor = ThemeManager.colors.buttonIcon.copy(alpha = 0.85f),
				uncheckedBorderColor = ThemeManager.colors.surface200.copy(alpha = 0.0f),
				disabledUncheckedTrackColor = ThemeManager.colors.surface200.copy(alpha = 0.7f),
				disabledUncheckedThumbColor = ThemeManager.colors.buttonIcon.copy(alpha = 0.7f),
				disabledUncheckedBorderColor = ThemeManager.colors.surface200.copy(alpha = 0.2f),
				disabledCheckedTrackColor = ThemeManager.colors.primaryAccent.copy(alpha = 0.7f),
				disabledCheckedThumbColor = ThemeManager.colors.background.copy(alpha = 0.7f),
				disabledCheckedBorderColor = ThemeManager.colors.surface200.copy(alpha = 0.2f),
			)
		)
	}
}