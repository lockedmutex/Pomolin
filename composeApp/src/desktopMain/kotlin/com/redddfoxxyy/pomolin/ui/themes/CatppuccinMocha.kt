package com.redddfoxxyy.pomolin.ui.themes

import androidx.compose.ui.graphics.Color
import com.redddfoxxyy.pomolin.ui.Theme
import com.redddfoxxyy.pomolin.ui.ThemeColors
import com.redddfoxxyy.pomolin.ui.ThemeType

data class CatppuccinMocha(
	override val themeType: ThemeType = ThemeType.Dark,
	override val author: String = "RedddFoxxyy",
	override val name: String = "Catppuccin Mocha",
	override val colors: ThemeColors = CatppuccinMochaColors(),

	) : Theme

data class CatppuccinMochaColors(
	override val base: Color = Color.hsl(240f, 0.21f, 0.15f),
	override val mantle: Color = Color.hsl(240f, 0.21f, 0.12f),
	override val crust: Color = Color.hsl(240f, 0.23f, 0.09f),
	override val surface: Color = Color.hsl(237f, 0.16f, 0.23f),
	override val surface100: Color = Color.hsl(234f, 0.13f, 0.31f),
	override val surface200: Color = Color.hsl(233f, 0.12f, 0.39f),
	override val text: Color = Color.hsl(226f, 0.64f, 0.88f),
	override val text100: Color = Color.hsl(227f, 0.44f, 0.80f),
	override val text200: Color = Color.hsl(228f, 0.29f, 0.73f),
	override val green: Color = Color.hsl(96f, 0.44f, 0.68f),
	override val red: Color = Color.hsl(343f, 0.81f, 0.75f),
	override val blue: Color = Color.hsl(217f, 0.92f, 0.78f),
	override val maroon: Color = Color.hsl(350f, 0.65f, 0.77f),
	override val sapphire: Color = Color.hsl(199f, 0.55f, 0.69f),
	override val mauve: Color = Color.hsl(267f, 0.84f, 0.81f),
	override val yellow: Color = Color.hsl(41f, 0.86f, 0.83f),
	override val peach: Color = Color.hsl(23f, 0.92f, 0.75f),
	override val lavender: Color = Color.hsl(232f, 0.97f, 0.85f),
) : ThemeColors


