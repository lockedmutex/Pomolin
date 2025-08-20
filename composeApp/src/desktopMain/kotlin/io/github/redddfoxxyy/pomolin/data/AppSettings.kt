package io.github.redddfoxxyy.pomolin.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import io.github.redddfoxxyy.pomolin.ui.ThemeManager
import org.apache.commons.lang3.SystemUtils
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

object AppSettings {
	var enableDarkMode by mutableStateOf(true)
	var enableProgressIndicator by mutableStateOf(true)
	var enableWindowDecorations by mutableStateOf(true)
	var enableWindowBorders by mutableStateOf(false)

	private val settingsProperties = Properties()
	private val configFile = getConfigurationFile()

	private fun getConfigurationFile(): File {
		val userHome = System.getProperty("user.home")
		val xdgConfig = System.getenv("XDG_CONFIG_HOME")?.let { File(it) }
		val configDir: File

		when {
			SystemUtils.IS_OS_WINDOWS -> {
				// Windows: %APPDATA%/Pomolin
				val appData = System.getenv("APPDATA")
				configDir =
					if (appData != null) File(appData, "Pomolin") else File(userHome, ".pomolin")
			}

			SystemUtils.IS_OS_MAC -> {
				// macOS: ~/Library/Application Support/Pomolin
				configDir = File(userHome, "Library/Application Support/Pomolin")
			}

			else -> {
				// Linux, flatpak sandbox and other Unix-like systems: ~/.config/Pomolin
				configDir = (xdgConfig ?: File(userHome, ".config")).resolve("Pomolin")
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
					enableDarkMode =
						settingsProperties.getProperty("enableDarkMode", "true")
							.toBoolean()
					enableProgressIndicator =
						settingsProperties.getProperty("enableProgressIndicator", "true")
							.toBoolean()
					enableWindowDecorations =
						settingsProperties.getProperty("enableWindowDecorations", "true")
							.toBoolean()
					enableWindowBorders =
						settingsProperties.getProperty("enableWindowBorders", "false").toBoolean()
				}
			} catch (e: Exception) {
				e.printStackTrace()
			}
		}
	}

	fun toggleDarkMode(state: Boolean) {
		ThemeManager.enableDarkMode(state)
		enableDarkMode = state
		saveSettings()
	}

	fun toggleProgressIndicator(state: Boolean) {
		enableProgressIndicator = state
		saveSettings()
	}

	fun toggleWindowDecorations(state: Boolean) {
		enableWindowDecorations = state
		enableWindowBorders = !enableWindowDecorations
		saveSettings()
	}

	fun toggleWindowBorders(state: Boolean) {
		enableWindowBorders = state
		saveSettings()
	}

	fun saveSettings() {
		try {
			FileOutputStream(configFile).use { output ->
				settingsProperties.setProperty(
					"enableDarkMode",
					(enableDarkMode).toString()
				)
				settingsProperties.setProperty(
					"enableProgressIndicator",
					(enableProgressIndicator).toString()
				)
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