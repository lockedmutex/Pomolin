package io.github.redddfoxxyy.pomolin.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

// TODO: Make this an extension of M3's theme manager.
internal object ThemeManager {
	var currentTheme by mutableStateOf<Theme>(CatppuccinMocha)
	val colors: ThemeColors
		get() = currentTheme.colors

	internal fun enableDarkMode(isDark: Boolean) {
		currentTheme = when (isDark) {
			false -> {
                CatppuccinLatteSoft
			}
			true -> {
                CatppuccinMocha
			}
		}
	}
}

enum class ThemeType {
	Dark,
	Light
}

interface Theme {
	val themeType: ThemeType
	val author: String
	val name: String
	val colors: ThemeColors
}

interface ThemeColors {
	val background: Color
	val boldText: Color
	val buttonIcon: Color
    val scrollBarAccent : Color
    val playButtonAccent: Color
    val pauseButtonAccent: Color
    val resetButtonAccent: Color
	val primaryAccent: Color
    val workAccent: Color
	val breakAccent: Color
	val longBreakAccent: Color
    val sessionsAccent: Color
	val surface: Color
	val surface100: Color
	val surface200: Color
    val text: Color
	val text100: Color
	val text200: Color
}


