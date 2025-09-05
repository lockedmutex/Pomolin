package io.github.redddfoxxyy.pomolin.ui.themes

import androidx.compose.ui.graphics.Color
import io.github.redddfoxxyy.pomolin.ui.Theme
import io.github.redddfoxxyy.pomolin.ui.ThemeColors
import io.github.redddfoxxyy.pomolin.ui.ThemeType

data class RosePineDawn(
	override val themeType: ThemeType = ThemeType.Light,
	override val author: String = "RedddFoxxyy",
	override val name: String = "Rosé Pine Dawn",
	override val colors: ThemeColors = RosePineDawnColors(),
) : Theme

data class RosePineDawnColors(
	override val base: Color = Color(0xFFfaf4ed),
	override val blue: Color = Color(0xFF286983),
	override val crust: Color = Color(0xFFf8f0e7),
	override val flamingo: Color = Color(0xFFd7827e),
	override val green: Color = Color(0xFF6d8f89),
	override val lavender: Color = Color(0xFF907aa9),
	override val mantle: Color = Color(0xFFfffaf3),
	override val maroon: Color = Color(0xFFb4637a),
	override val mauve: Color = Color(0xFF907aa9),
	override val overlay0: Color = Color(0xFFf2e9e1),
	override val overlay1: Color = Color(0xFFdfdad9),
	override val overlay2: Color = Color(0xFFcecacd),
	override val peach: Color = Color(0xFFea9d34),
	override val pink: Color = Color(0xFFd7827e),
	override val red: Color = Color(0xFFb4637a),
	override val rosewater: Color = Color(0xFFd7827e),
	override val sapphire: Color = Color(0xFF56949f),
	override val sky: Color = Color(0xFF56949f),
	override val subtext0: Color = Color(0xFF797593),
	override val subtext1: Color = Color(0xFF9893a5),
	override val surface0: Color = Color(0xFFfffaf3),
	override val surface1: Color = Color(0xFFf4ede8),
	override val surface2: Color = Color(0xFFdfdad9),
	override val teal: Color = Color(0xFF56949f),
	override val text: Color = Color(0xFF464261),
	override val yellow: Color = Color(0xFFea9d34),

	override val background: Color = Color(0xFFfaf4ed),
	override val boldText: Color = Color(0xFF464261),
	override val buttonIcon: Color = Color(0xFFfffaf3),
	override val primaryAccent: Color = Color(0xFF907aa9),
	override val primaryBreak: Color = Color(0xFFea9d34),
	override val primaryLBreak: Color = Color(0xFFd7827e),
	override val surface: Color = Color(0xFFfffaf3),
	override val surface100: Color = Color(0xFFf4ede8),
	override val surface200: Color = Color(0xFFdfdad9),
	override val text100: Color = Color(0xFF9893a5),
	override val text200: Color = Color(0xFF797593),

	) : ThemeColors
