package io.github.redddfoxxyy.pomolin.ui

import androidx.compose.ui.graphics.Color

data object CatppuccinMocha: Theme {
    override val themeType: ThemeType = ThemeType.Dark
    override val author: String = "RedddFoxxyy"
    override val name: String = "Catppuccin Mocha"

    override val colors: ThemeColors = object : ThemeColors {
        override val background: Color = Color(0xFF1E1E2E)
        override val buttonIcon: Color = Color(0xFF12121C)
        override val scrollBarAccent: Color = Color(0xFFCAA6F7)
        override val playButtonAccent: Color = Color(0xFFA6E3A1)
        override val pauseButtonAccent: Color = Color(0xFFFAB285)
        override val resetButtonAccent: Color = Color(0xFFF38CA9)
        override val primaryAccent: Color = Color(0xFFCAA6F7)
        override val workAccent: Color = Color(0xFFCAA6F7)
        override val breakAccent: Color = Color(0xFFF9E1AE)
        override val longBreakAccent: Color = Color(0xFFFAB285)
        override val sessionsAccent: Color = Color(0xFF89B5FA)
        override val surface: Color = Color(0xFF313244)
        override val surface100: Color = Color(0xFF454759)
        override val surface200: Color = Color(0xFF585A6F)
        override val text: Color = Color(0xFFCDD6F4)
        override val boldText: Color = Color(0xFFB4BEFE)
        override val text100: Color = Color(0xFFBAC2DE)
        override val text200: Color = Color(0xFFA6ADC9)
    }
}

data object CatppuccinLatteSoft: Theme {
    override val themeType: ThemeType = ThemeType.Light
    override val author: String = "RedddFoxxyy"
    override val name: String = "Soft Light"

    override val colors: ThemeColors = object : ThemeColors {
        override val background: Color = Color(0xFFF9FAFB)
        override val buttonIcon: Color = Color(0xFFEFF2F5)
        override val scrollBarAccent: Color = Color(0xFFB689CD)
        override val playButtonAccent: Color = Color(0xFF4EBC4E)
        override val pauseButtonAccent: Color = Color(0xFFD9A37D)
        override val resetButtonAccent: Color = Color(0xFFD36969)
        override val primaryAccent: Color = Color(0xFFB689CD)
        override val workAccent: Color = Color(0xFFB689CD)
        override val breakAccent: Color = Color(0xFFD8BB64)
        override val longBreakAccent: Color = Color(0xFFD9A37D)
        override val sessionsAccent: Color = Color(0xFF649ED8)
        override val surface: Color = Color(0xFFE4E8EC)
        override val surface100: Color = Color(0xFFD9DEE2)
        override val surface200: Color = Color(0xFFCED4D9)
        override val text: Color = Color(0xFF2A3846)
        override val boldText: Color = Color(0xFF202E3C)
        override val text100: Color = Color(0xFF4B5E71)
        override val text200: Color = Color(0xFF62788D)
    }
}