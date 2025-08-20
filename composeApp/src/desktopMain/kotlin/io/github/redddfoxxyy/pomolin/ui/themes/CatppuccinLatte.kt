package io.github.redddfoxxyy.pomolin.ui.themes

import androidx.compose.ui.graphics.Color
import io.github.redddfoxxyy.pomolin.ui.Theme
import io.github.redddfoxxyy.pomolin.ui.ThemeColors
import io.github.redddfoxxyy.pomolin.ui.ThemeType

data class CatppuccinLatte(
	override val themeType: ThemeType = ThemeType.Light,
	override val author: String = "RedddFoxxyy",
	override val name: String = "Catppuccin Latte",
	override val colors: ThemeColors = CatppuccinLatteColors(),
) : Theme

data class CatppuccinLatteColors(
	override val base: Color = Color.hsl(220f, 0.23f, 0.95f),
	override val blue: Color = Color.hsl(220f, 0.91f, 0.54f),
	override val crust: Color = Color.hsl(220f, 0.21f, 0.89f),
	override val flamingo: Color = Color.hsl(0f, 0.60f, 0.67f),
	override val green: Color = Color.hsl(109f, 0.58f, 0.40f),
	override val lavender: Color = Color.hsl(231f, 0.97f, 0.72f),
	override val mantle: Color = Color.hsl(220f, 0.22f, 0.92f),
	override val maroon: Color = Color.hsl(355f, 0.76f, 0.59f),
	override val mauve: Color = Color.hsl(266f, 0.85f, 0.58f),
	override val overlay0: Color = Color.hsl(228f, 0.11f, 0.65f),
	override val overlay1: Color = Color.hsl(231f, 0.10f, 0.59f),
	override val overlay2: Color = Color.hsl(232f, 0.10f, 0.53f),
	override val peach: Color = Color.hsl(22f, 0.99f, 0.52f),
	override val pink: Color = Color.hsl(316f, 0.73f, 0.69f),
	override val red: Color = Color.hsl(347f, 0.87f, 0.44f),
	override val rosewater: Color = Color.hsl(11f, 0.59f, 0.67f),
	override val sapphire: Color = Color.hsl(189f, 0.70f, 0.42f),
	override val sky: Color = Color.hsl(197f, 0.97f, 0.46f),
	override val subtext0: Color = Color.hsl(233f, 0.10f, 0.47f),
	override val subtext1: Color = Color.hsl(233f, 0.13f, 0.41f),
	override val surface0: Color = Color.hsl(223f, 0.16f, 0.83f),
	override val surface1: Color = Color.hsl(225f, 0.14f, 0.77f),
	override val surface2: Color = Color.hsl(227f, 0.12f, 0.71f),
	override val teal: Color = Color.hsl(183f, 0.74f, 0.35f),
	override val text: Color = Color.hsl(234f, 0.16f, 0.35f),
	override val yellow: Color = Color.hsl(35f, 0.77f, 0.49f),

	override val background: Color = Color.hsl(220f, 0.23f, 0.95f),    // Base
	override val boldText: Color = Color.hsl(236f, 0.16f, 0.25f),      // Blue
	override val buttonIcon: Color = Color.hsl(220f, 0.21f, 0.89f),    // Subtext1
	override val primaryAccent: Color = Color.hsl(266f, 0.85f, 0.58f), // Mauve
	override val primaryBreak: Color = Color.hsl(35f, 0.77f, 0.49f),   // Yellow
	override val primaryLBreak: Color = Color.hsl(22f, 0.99f, 0.52f),  // Peach
	override val surface: Color = Color.hsl(223f, 0.16f, 0.83f),       // Surface0
	override val surface100: Color = Color.hsl(225f, 0.14f, 0.77f),    // Surface1
	override val surface200: Color = Color.hsl(227f, 0.12f, 0.71f),    // Surface2
	override val text100: Color = Color.hsl(233f, 0.13f, 0.41f),       // Subtext1
	override val text200: Color = Color.hsl(233f, 0.10f, 0.47f),       // Subtext0

) : ThemeColors
