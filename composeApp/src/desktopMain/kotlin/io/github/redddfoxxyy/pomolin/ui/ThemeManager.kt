package io.github.redddfoxxyy.pomolin.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import io.github.redddfoxxyy.pomolin.ui.themes.CatppuccinMocha
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

// TODO: Make this an extension of M3's theme manager.
internal object ThemeManager {
    val currentTheme = mutableStateOf(CatppuccinMocha())
    val colors = currentTheme.value.colors
    val mode = mutableStateOf(ThemeType.Dark)

    var enableWindowDecorations by mutableStateOf(false)
    var enableWindowBorders by mutableStateOf(true)

    private val properties = Properties()
    private val configFile = getConfigurationFile()

    internal fun toggleTheme() {
        mode.value = when (mode.value) {
            ThemeType.Dark -> ThemeType.Light
            ThemeType.Light -> ThemeType.Dark
        }
    }

    // TODO: Move this to appSettings class
    private fun getConfigurationFile(): File {
        val os = System.getProperty("os.name").lowercase()
        val userHome = System.getProperty("user.home")
        val configDir: File

        when {
            "win" in os -> {
                // Windows: %APPDATA%/Pomolin
                val appData = System.getenv("APPDATA")
                configDir = if (appData != null) File(appData, "Pomolin") else File(userHome, ".pomolin")
            }

            "mac" in os -> {
                // macOS: ~/Library/Application Support/Pomolin
                configDir = File(userHome, "Library/Application Support/Pomolin")
            }

            else -> {
                // Linux and others: ~/.config/Pomolin
                configDir = File(userHome, ".config/Pomolin")
            }
        }

        // Create the directory if it doesn't exist
        if (!configDir.exists()) {
            configDir.mkdirs()
        }

        return File(configDir, "config.properties")
    }

    private fun loadSettings() {
        if (configFile.exists()) {
            try {
                FileInputStream(configFile).use { input ->
                    properties.load(input)
                    enableWindowDecorations = properties.getProperty("enableWindowDecorations", "true").toBoolean()
                    enableWindowBorders = properties.getProperty("enableWindowBorders", "false").toBoolean()
                }
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            }
        }
    }

    fun toggleWindowDecorations() {
        try {
            FileOutputStream(configFile).use { output ->
                properties.setProperty("enableWindowDecorations", (!enableWindowDecorations).toString())
                properties.setProperty("enableWindowBorders", (!enableWindowBorders).toString())
                properties.store(output, "Pomolin App Settings")
            }
        } catch (e: Exception) {
            // Handle error
            e.printStackTrace()
        }
    }

    fun saveSettings() {
        try {
            FileOutputStream(configFile).use { output ->
                properties.setProperty("enableWindowDecorations", enableWindowDecorations.toString())
                properties.setProperty("enableWindowBorders", enableWindowBorders.toString())
                properties.store(output, "Pomolin App Settings")
            }
        } catch (e: Exception) {
            // Handle error
            e.printStackTrace()
        }
    }

    init {
        loadSettings()
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

    val crust: Color
    val mantle: Color
    val base: Color
    val surface: Color
    val surface100: Color
    val surface200: Color
    val text: Color
    val text100: Color
    val text200: Color
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


