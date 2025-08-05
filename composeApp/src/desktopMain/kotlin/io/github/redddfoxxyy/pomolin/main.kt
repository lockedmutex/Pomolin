package io.github.redddfoxxyy.pomolin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import io.github.redddfoxxyy.pomolin.ui.ThemeManager
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import pomolin.composeapp.generated.resources.JetBrainsMonoNerdFont_ExtraBold
import pomolin.composeapp.generated.resources.Pomolin
import pomolin.composeapp.generated.resources.Res
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
        undecorated = !ThemeManager.enableWindowDecorations,
        transparent = !ThemeManager.enableWindowDecorations,
        state = windowState,
        icon = icon,
    ) {
        SwingUtilities.getWindowAncestor(this.window.rootPane)?.apply {
            minimumSize = Dimension(minWindowDimensions.first, minWindowDimensions.second)
        }

        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = ThemeManager.colors.base,
            shape = if (!ThemeManager.enableWindowDecorations) {
                RoundedCornerShape(15.dp)
            } else {
                RoundedCornerShape(0.dp)
            },
            border = if (ThemeManager.enableWindowBorders) {
                BorderStroke(2.dp, ThemeManager.colors.mauve.copy(alpha = 0.8f))
            } else null,
        ) {
            Column {
                if (!ThemeManager.enableWindowDecorations) {
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
                        }
                    }
                }
                App()
            }
        }
    }
}


