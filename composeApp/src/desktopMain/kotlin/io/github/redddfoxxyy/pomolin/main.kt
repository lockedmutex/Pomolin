package io.github.redddfoxxyy.pomolin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import pomolin.composeapp.generated.resources.JetBrainsMonoNerdFont_ExtraBold
import pomolin.composeapp.generated.resources.Pomolin
import pomolin.composeapp.generated.resources.Res
import pomolin.composeapp.generated.resources.close
import pomolin.composeapp.generated.resources.maximize
import pomolin.composeapp.generated.resources.restore
import java.awt.Dimension
import javax.swing.SwingUtilities

fun main() = application {
	val icon = painterResource(Res.drawable.Pomolin)
	val minWindowDimensions = Pair(390, 540)
	val windowState = rememberWindowState(
		width = minWindowDimensions.first.dp,
		height = minWindowDimensions.second.dp
	)
	val jetbrainsMono = FontFamily(Font(Res.font.JetBrainsMonoNerdFont_ExtraBold))

	Window(
		onCloseRequest = ::exitApplication,
		title = "Pomolin",
//		resizable = false,
		undecorated = !AppSettings.enableWindowDecorations,
		transparent = !AppSettings.enableWindowDecorations,
		state = windowState,
		icon = icon,
	) {
		SwingUtilities.getWindowAncestor(this.window.rootPane)?.apply {
			minimumSize = Dimension(minWindowDimensions.first, minWindowDimensions.second)
		}

		Surface(
			modifier = Modifier.fillMaxWidth(),
			color = ThemeManager.colors.base,
			shape = if (!AppSettings.enableWindowDecorations) {
				RoundedCornerShape(15.dp)
			} else {
				RoundedCornerShape(0.dp)
			},
			border = if (AppSettings.enableWindowBorders) {
				BorderStroke(2.dp, ThemeManager.colors.mauve.copy(alpha = 0.8f))
			} else null,
		) {
			Column {
				if (!AppSettings.enableWindowDecorations) {
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
								color = ThemeManager.colors.mauve
							)
							Box(
								modifier = Modifier
									.padding(end = 8.dp, top = 2.dp)
									.align(Alignment.TopEnd)
									.size(22.dp) // This now correctly controls the background circle size
									.background(
										color = ThemeManager.colors.surface.copy(alpha = 0.8f),
										shape = CircleShape
									),
								contentAlignment = Alignment.Center
							) {
								IconButton(
									onClick = { exitApplication() },
									modifier = Modifier
//										.padding(end = 10.dp, top = 1.dp)
										.size(22.dp) // Smaller size for just the button
										.align(Alignment.TopEnd),
//									.background(
//										color = ThemeManager.colors.surface,
//										shape = CircleShape // Explicit circle shape
//									),
									colors = IconButtonDefaults.iconButtonColors(
										containerColor = Color.Transparent, // Button itself has mauve background
										contentColor = ThemeManager.colors.mauve.copy(alpha = 0.8f), // Icon color
									),
								) {
									Icon(
										painterResource(Res.drawable.close),
										contentDescription = "Close App",
										modifier = Modifier.size(16.dp) // Control icon size
									)
								}
							}
							Box(
								modifier = Modifier
									.padding(end = 36.dp, top = 2.dp)
									.align(Alignment.TopEnd)
									.size(22.dp) // This now correctly controls the background circle size
									.background(
										color = ThemeManager.colors.surface.copy(alpha = 0.8f),
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
//										.padding(end = 10.dp, top = 1.dp)
										.size(22.dp) // Smaller size for just the button
										.align(Alignment.TopEnd),
//									.background(
//										color = ThemeManager.colors.surface,
//										shape = CircleShape // Explicit circle shape
//									),
									colors = IconButtonDefaults.iconButtonColors(
										containerColor = Color.Transparent, // Button itself has mauve background
										contentColor = ThemeManager.colors.mauve.copy(alpha = 0.8f), // Icon color
									),
								) {
									Icon(
										painterResource(
											if (windowState.placement == WindowPlacement.Maximized) {
												Res.drawable.restore // Icon when maximized (shows restore icon)
											} else {
												Res.drawable.maximize // Icon when not maximized (shows maximize icon)
											}
										),
										contentDescription = if (windowState.placement == WindowPlacement.Maximized) {
											"Restore Window"
										} else {
											"Maximize Window"
										},
										modifier = Modifier.size(16.dp) // Control icon size
									)
								}
							}
						}
					}
				}
				App()
			}
		}
	}
}


