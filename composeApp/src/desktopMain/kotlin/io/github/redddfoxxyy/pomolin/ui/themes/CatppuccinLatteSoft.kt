package io.github.redddfoxxyy.pomolin.ui.themes

import androidx.compose.ui.graphics.Color
import io.github.redddfoxxyy.pomolin.ui.Theme
import io.github.redddfoxxyy.pomolin.ui.ThemeColors
import io.github.redddfoxxyy.pomolin.ui.ThemeType

data class CatppuccinLatteSoft(
	override val themeType: ThemeType = ThemeType.Light,
	override val author: String = "RedddFoxxyy",
	override val name: String = "Soft Light",
	override val colors: ThemeColors = CatppuccinLatteSoftColors(),
) : Theme

data class CatppuccinLatteSoftColors(
	override val base: Color = Color.hsl(210f, 0.20f, 0.98f),
	override val blue: Color = Color.hsl(210f, 0.60f, 0.62f),
	override val crust: Color = Color.hsl(210f, 0.25f, 0.95f),
	override val flamingo: Color = Color.hsl(0f, 0.50f, 0.72f),
	override val green: Color = Color.hsl(120f, 0.45f, 0.52f),
	override val lavender: Color = Color.hsl(270f, 0.35f, 0.72f),
	override val mantle: Color = Color.hsl(210f, 0.23f, 0.96f),
	override val maroon: Color = Color.hsl(350f, 0.45f, 0.62f),
	override val mauve: Color = Color.hsl(280f, 0.40f, 0.67f),
	override val overlay0: Color = Color.hsl(210f, 0.12f, 0.62f),
	override val overlay1: Color = Color.hsl(210f, 0.15f, 0.55f),
	override val overlay2: Color = Color.hsl(210f, 0.18f, 0.47f),
	override val peach: Color = Color.hsl(25f, 0.55f, 0.67f),
	override val pink: Color = Color.hsl(320f, 0.45f, 0.72f),
	override val red: Color = Color.hsl(0f, 0.55f, 0.62f),
	override val rosewater: Color = Color.hsl(10f, 0.35f, 0.82f),
	override val sapphire: Color = Color.hsl(200f, 0.50f, 0.57f),
	override val sky: Color = Color.hsl(190f, 0.45f, 0.62f),
	override val subtext0: Color = Color.hsl(210f, 0.20f, 0.42f),
	override val subtext1: Color = Color.hsl(210f, 0.18f, 0.32f),
	override val surface0: Color = Color.hsl(210f, 0.16f, 0.91f),
	override val surface1: Color = Color.hsl(210f, 0.14f, 0.87f),
	override val surface2: Color = Color.hsl(210f, 0.12f, 0.83f),
	override val teal: Color = Color.hsl(180f, 0.40f, 0.57f),
	override val text: Color = Color.hsl(210f, 0.25f, 0.22f),
	override val yellow: Color = Color.hsl(45f, 0.60f, 0.62f),

	override val background: Color = Color.hsl(210f, 0.20f, 0.98f),
	override val boldText: Color = Color.hsl(210f, 0.30f, 0.18f),
	override val buttonIcon: Color = Color.hsl(210f, 0.25f, 0.95f),
	override val primaryAccent: Color = Color.hsl(280f, 0.40f, 0.67f),
	override val primaryBreak: Color = Color.hsl(45f, 0.60f, 0.62f),
	override val primaryLBreak: Color = Color.hsl(25f, 0.55f, 0.67f),
	override val surface: Color = Color.hsl(210f, 0.16f, 0.91f),
	override val surface100: Color = Color.hsl(210f, 0.14f, 0.87f),
	override val surface200: Color = Color.hsl(210f, 0.12f, 0.83f),
	override val text100: Color = Color.hsl(210f, 0.20f, 0.37f),
	override val text200: Color = Color.hsl(210f, 0.18f, 0.47f),

	) : ThemeColors

