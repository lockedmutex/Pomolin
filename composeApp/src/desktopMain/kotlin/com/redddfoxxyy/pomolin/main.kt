package com.redddfoxxyy.pomolin

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.jetbrains.compose.resources.painterResource
import pomolin.composeapp.generated.resources.Pomolin
import pomolin.composeapp.generated.resources.Res
import java.awt.Dimension
import javax.swing.SwingUtilities

fun main() = application {
	val icon = painterResource(Res.drawable.Pomolin)
	val minWindowDimensions = Pair(380, 500)
	Window(
		onCloseRequest = ::exitApplication,
		title = "Pomolin",
		resizable = true,
		state = rememberWindowState(
			width = minWindowDimensions.first.dp,
			height = minWindowDimensions.second.dp
		),
		icon = icon,
	) {
		SwingUtilities.getWindowAncestor(this.window.rootPane)?.apply {
			minimumSize = Dimension(minWindowDimensions.first, minWindowDimensions.second)
		}
		App()
	}
}