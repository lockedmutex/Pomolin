package com.redddfoxxyy.pomolin.ui

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.redddfoxxyy.pomolin.ui.themes.CatppuccinMocha

// TODO: Make this an extension of M3's theme manager.
internal object ThemeManager {
	val currentTheme = mutableStateOf(CatppuccinMocha())
	val colors = currentTheme.value.colors
	val mode = mutableStateOf(ThemeType.Dark)

	internal fun toggleTheme() {
		mode.value = when (mode.value) {
			ThemeType.Dark -> ThemeType.Light
			ThemeType.Light -> ThemeType.Dark
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
	val base: Color
	val crust: Color
	val text: Color
	val textLight: Color
	val textDark: Color
	val green: Color
	val red: Color
	val blue: Color
	val maroon: Color
	val sapphire: Color
	val mauve: Color
	val yellow: Color
	val peach: Color
	val lavender: Color
}


