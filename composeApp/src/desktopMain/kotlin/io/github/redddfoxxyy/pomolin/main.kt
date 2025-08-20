package io.github.redddfoxxyy.pomolin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import io.github.redddfoxxyy.pomolin.data.AppSettings
import io.github.redddfoxxyy.pomolin.ui.ThemeManager
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import pomolin.composeapp.generated.resources.*
import java.awt.Dimension
import javax.swing.SwingUtilities

fun main() = application {
	var windowRestartTrigger by remember { mutableStateOf(false) }
	var windowDecorations by remember { mutableStateOf(AppSettings.enableWindowDecorations) }

	val icon = painterResource(Res.drawable.Pomolin)
	val minWindowDimensions = Pair(390, 540)
	val jetbrainsMono = FontFamily(Font(Res.font.JetBrainsMonoNerdFont_ExtraBold))

	// Restart the window when settings change
	fun restartWindow() {
		if (windowDecorations != AppSettings.enableWindowDecorations) {
			windowDecorations = AppSettings.enableWindowDecorations
			windowRestartTrigger = !windowRestartTrigger
		}
	}

	key(windowRestartTrigger) {
		val windowState = rememberWindowState(
			width = minWindowDimensions.first.dp,
			height = minWindowDimensions.second.dp
		)

		Window(
			onCloseRequest = ::exitApplication,
			title = "Pomolin",
			undecorated = !windowDecorations,
			transparent = !windowDecorations,
			state = windowState,
			icon = icon,
		) {
			SwingUtilities.getWindowAncestor(this.window.rootPane)?.apply {
				minimumSize = Dimension(minWindowDimensions.first, minWindowDimensions.second)
			}

			Surface(
				modifier = Modifier.fillMaxWidth(),
				color = ThemeManager.colors.background,
				shape = if (!windowDecorations) {
					RoundedCornerShape(15.dp)
				} else {
					RoundedCornerShape(0.dp)
				},
				border = if (AppSettings.enableWindowBorders) {
					BorderStroke(2.dp, ThemeManager.colors.primaryAccent.copy(alpha = 0.8f))
				} else null,
			) {
				Column {
					if (!windowDecorations) {
						WindowDraggableArea {
							Box(
								modifier = Modifier
									.fillMaxWidth()
									.height(38.dp)
									.background(Color.Transparent)
									.padding(top = 8.dp)
							) {
								Text(
									text = "Pomolin",
									modifier = Modifier.align(Alignment.Center),
									fontFamily = jetbrainsMono,
									fontSize = 18.sp,
									color = ThemeManager.colors.primaryAccent
								)
								Box(
									modifier = Modifier
										.padding(end = 8.dp, top = 2.dp)
										.align(Alignment.TopEnd)
										.size(24.dp)
										.background(
											color = ThemeManager.colors.red.copy(alpha = 0.8f),
											shape = CircleShape
										),
									contentAlignment = Alignment.Center
								) {
									IconButton(
										onClick = { exitApplication() },
										modifier = Modifier
											.size(24.dp)
											.align(Alignment.TopEnd),
										colors = IconButtonDefaults.iconButtonColors(
											containerColor = Color.Transparent,
											contentColor = ThemeManager.colors.buttonIcon.copy(alpha = 0.8f),
										),
									) {
										Icon(
											painterResource(Res.drawable.close),
											contentDescription = "Close App",
											modifier = Modifier.size(18.dp)
										)
									}
								}
								Box(
									modifier = Modifier
										.padding(end = 36.dp, top = 2.dp)
										.align(Alignment.TopEnd)
										.size(24.dp)
										.background(
											color = ThemeManager.colors.green.copy(alpha = 0.8f),
											shape = CircleShape
										),
									contentAlignment = Alignment.Center
								) {
									IconButton(
										onClick = {
											windowState.placement =
												if (windowState.placement == WindowPlacement.Maximized) {
													WindowPlacement.Floating
												} else {
													WindowPlacement.Maximized
												}
										},
										modifier = Modifier
											.size(24.dp)
											.align(Alignment.TopEnd),
										colors = IconButtonDefaults.iconButtonColors(
											containerColor = Color.Transparent,
											contentColor = ThemeManager.colors.buttonIcon.copy(alpha = 0.8f),
										),
									) {
										Icon(
											painterResource(
												if (windowState.placement == WindowPlacement.Maximized) {
													Res.drawable.restore
												} else {
													Res.drawable.maximize
												}
											),
											contentDescription = if (windowState.placement == WindowPlacement.Maximized) {
												"Restore Window"
											} else {
												"Maximize Window"
											},
											modifier = Modifier.size(20.dp)
										)
									}
								}
							}
						}
					}

					App { restartWindow() }
				}
			}
		}
	}
}
