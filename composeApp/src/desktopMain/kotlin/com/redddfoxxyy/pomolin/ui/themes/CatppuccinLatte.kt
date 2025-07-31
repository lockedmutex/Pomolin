package com.redddfoxxyy.pomolin.ui.themes

import androidx.compose.ui.graphics.Color
import com.redddfoxxyy.pomolin.ui.Theme
import com.redddfoxxyy.pomolin.ui.ThemeColors
import com.redddfoxxyy.pomolin.ui.ThemeType

data class CatppuccinLatte(
	override val themeType: ThemeType = ThemeType.Light,
	override val author: String = "RedddFoxxyy",
	override val name: String = "Catppuccin Latte",
	override val colors: ThemeColors = CatppuccinLatteColors(),

	) : Theme

data class CatppuccinLatteColors(
	override val base: Color = Color.hsl(220f, 0.23f, 0.95f),
	override val crust: Color = Color.hsl(220f, 0.21f, 0.89f),
	override val text: Color = Color.hsl(234f, 0.16f, 0.35f),
	override val textLight: Color = Color.hsl(233f, 0.10f, 0.41f),
	override val textDark: Color = Color.hsl(233f, 0.10f, 0.47f),
	override val green: Color = Color.hsl(109f, 0.58f, 0.40f),
	override val red: Color = Color.hsl(347f, 0.87f, 0.44f),
	override val blue: Color = Color.hsl(217f, 0.92f, 0.78f),
	override val maroon: Color = Color.hsl(355f, 0.76f, 0.59f),
	override val sapphire: Color = Color.hsl(199f, 0.55f, 0.69f),
	override val mauve: Color = Color.hsl(266f, 0.85f, 0.58f),
	override val yellow: Color = Color.hsl(41f, 0.86f, 0.83f),
	override val peach: Color = Color.hsl(22f, 0.92f, 0.75f),
	override val lavender: Color = Color.hsl(231f, 0.99f, 0.52f),
) : ThemeColors
