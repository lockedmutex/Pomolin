package io.github.redddfoxxyy.pomolin.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.Properties

object AppSettings {
    var enableProgressIndicator by mutableStateOf(true)
    var enableWindowDecorations by mutableStateOf(true)
    var enableWindowBorders by mutableStateOf(false)

    private val settingsProperties = Properties()
    private val configFile = getConfigurationFile()

    private fun getConfigurationFile(): File {
        val os = System.getProperty("os.name").lowercase()
        val userHome = System.getProperty("user.home")
        val configDir: File

        when {
            "win" in os -> {
                // Windows: %APPDATA%/Pomolin
                val appData = System.getenv("APPDATA")
                configDir =
                    if (appData != null) File(appData, "Pomolin") else File(userHome, ".pomolin")
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
                    settingsProperties.load(input)
                    enableWindowDecorations =
                        settingsProperties.getProperty("enableWindowDecorations", "true")
                            .toBoolean()
                    enableWindowBorders =
                        settingsProperties.getProperty("enableWindowBorders", "false").toBoolean()
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
                settingsProperties.setProperty(
                    "enableWindowDecorations",
                    (!enableWindowDecorations).toString()
                )
                settingsProperties.setProperty(
                    "enableWindowBorders",
                    (!enableWindowBorders).toString()
                )
                settingsProperties.store(output, "Pomolin App Settings")
            }
        } catch (e: Exception) {
            // Handle error
            e.printStackTrace()
        }
    }

    fun saveSettings() {
        try {
            FileOutputStream(configFile).use { output ->
                settingsProperties.setProperty(
                    "enableWindowDecorations",
                    enableWindowDecorations.toString()
                )
                settingsProperties.setProperty(
                    "enableWindowBorders",
                    enableWindowBorders.toString()
                )
                settingsProperties.store(output, "Pomolin App Settings")
            }
        } catch (e: Exception) {
            // Handle error
            e.printStackTrace()
        }
    }

    init {
        loadSettings()
        if (enableWindowDecorations) {
            enableWindowBorders = false
        }
    }
}