package io.github.redddfoxxyy.pomolin.ui.themes

import androidx.compose.ui.graphics.Color
import io.github.redddfoxxyy.pomolin.ui.Theme
import io.github.redddfoxxyy.pomolin.ui.ThemeColors
import io.github.redddfoxxyy.pomolin.ui.ThemeType

data class CatppuccinMocha(
	override val themeType: ThemeType = ThemeType.Dark,
	override val author: String = "RedddFoxxyy",
	override val name: String = "Catppuccin Mocha",
	override val colors: ThemeColors = CatppuccinMochaColors(),
) : Theme

data class CatppuccinMochaColors(
	override val base: Color = Color.hsl(240f, 0.21f, 0.15f),
	override val blue: Color = Color.hsl(217f, 0.92f, 0.76f),
	override val crust: Color = Color.hsl(240f, 0.23f, 0.09f),
	override val flamingo: Color = Color.hsl(0f, 0.59f, 0.88f),
	override val green: Color = Color.hsl(115f, 0.54f, 0.76f),
	override val lavender: Color = Color.hsl(232f, 0.97f, 0.85f),
	override val mantle: Color = Color.hsl(240f, 0.21f, 0.12f),
	override val maroon: Color = Color.hsl(350f, 0.65f, 0.77f),
	override val mauve: Color = Color.hsl(267f, 0.84f, 0.81f),
	override val overlay0: Color = Color.hsl(231f, 0.11f, 0.47f),
	override val overlay1: Color = Color.hsl(230f, 0.13f, 0.55f),
	override val overlay2: Color = Color.hsl(228f, 0.17f, 0.64f),
	override val peach: Color = Color.hsl(23f, 0.92f, 0.75f),
	override val pink: Color = Color.hsl(316f, 0.72f, 0.86f),
	override val red: Color = Color.hsl(343f, 0.81f, 0.75f),
	override val rosewater: Color = Color.hsl(10f, 0.56f, 0.91f),
	override val sapphire: Color = Color.hsl(199f, 0.76f, 0.69f),
	override val sky: Color = Color.hsl(189f, 0.71f, 0.73f),
	override val subtext0: Color = Color.hsl(228f, 0.24f, 0.72f),
	override val subtext1: Color = Color.hsl(227f, 0.35f, 0.80f),
	override val surface0: Color = Color.hsl(237f, 0.16f, 0.23f),
	override val surface1: Color = Color.hsl(234f, 0.13f, 0.31f),
	override val surface2: Color = Color.hsl(233f, 0.12f, 0.39f),
	override val teal: Color = Color.hsl(170f, 0.57f, 0.73f),
	override val text: Color = Color.hsl(226f, 0.64f, 0.88f),
	override val yellow: Color = Color.hsl(41f, 0.86f, 0.83f),

	override val background: Color = Color.hsl(240f, 0.21f, 0.15f),    // Base
	override val boldText: Color = Color.hsl(232f, 0.97f, 0.85f),      // Lavender
	override val buttonIcon: Color = Color.hsl(240f, 0.23f, 0.09f),    // Subtext1
	override val primaryAccent: Color = Color.hsl(267f, 0.84f, 0.81f), // Mauve
	override val primaryBreak: Color = Color.hsl(41f, 0.86f, 0.83f),   // Yellow
	override val primaryLBreak: Color = Color.hsl(23f, 0.92f, 0.75f),  // Peach
	override val surface: Color = Color.hsl(237f, 0.16f, 0.23f),       // Surface0
	override val surface100: Color = Color.hsl(234f, 0.13f, 0.31f),    // Surface1
	override val surface200: Color = Color.hsl(233f, 0.12f, 0.39f),    // Surface2
	override val text100: Color = Color.hsl(227f, 0.35f, 0.80f),       // Subtext1
	override val text200: Color = Color.hsl(228f, 0.24f, 0.72f),       // Subtext0

) : ThemeColors

