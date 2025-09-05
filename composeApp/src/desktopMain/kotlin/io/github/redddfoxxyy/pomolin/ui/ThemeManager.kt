package io.github.redddfoxxyy.pomolin.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import io.github.redddfoxxyy.pomolin.data.AppSettings
import io.github.redddfoxxyy.pomolin.ui.themes.CatppuccinLatteSoft
import io.github.redddfoxxyy.pomolin.ui.themes.CatppuccinMocha

// TODO: Make this an extension of M3's theme manager.
internal object ThemeManager {
	var currentTheme by mutableStateOf<Theme>(CatppuccinMocha())
	val colors: ThemeColors
		get() = currentTheme.colors
	var mode by mutableStateOf(ThemeType.Dark)

	internal fun enableDarkMode(isDark: Boolean) {
		when (isDark) {
			false -> {
				mode = ThemeType.Light
				AppSettings.enableDarkMode = false
				currentTheme = CatppuccinLatteSoft()
			}

			true -> {
				mode = ThemeType.Dark
				AppSettings.enableDarkMode = true
				currentTheme = CatppuccinMocha()
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
	val primaryAccent: Color
	val primaryBreak: Color
	val primaryLBreak: Color
	val surface: Color
	val surface100: Color
	val surface200: Color
	val text100: Color
	val text200: Color

	val base: Color
	val blue: Color
	val crust: Color
	val flamingo: Color
	val green: Color
	val lavender: Color
	val mantle: Color
	val maroon: Color
	val mauve: Color
	val overlay0: Color
	val overlay1: Color
	val overlay2: Color
	val peach: Color
	val pink: Color
	val red: Color
	val rosewater: Color
	val sapphire: Color
	val sky: Color
	val subtext0: Color
	val subtext1: Color
	val surface0: Color
	val surface1: Color
	val surface2: Color
	val teal: Color
	val text: Color
	val yellow: Color

}


