package io.github.redddfoxxyy.pomolin.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.apache.commons.lang3.SystemUtils
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

enum class ThemeMode {
	Light, Dark, Automatic
}

object AppSettings {
	var themeMode by mutableStateOf(ThemeMode.Automatic)
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
				val appData = System.getenv("APPDATA")
				configDir =
					if (appData != null) File(appData, "Pomolin") else File(userHome, ".pomolin")
			}

			SystemUtils.IS_OS_MAC -> {
				configDir = File(userHome, "Library/Application Support/Pomolin")
			}

			else -> {
				configDir = (xdgConfig ?: File(userHome, ".config")).resolve("Pomolin")
			}
		}

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
					themeMode =
						ThemeMode.valueOf(settingsProperties.getProperty("themeMode", ThemeMode.Dark.name))
					enableProgressIndicator =
						settingsProperties.getProperty("enableProgressIndicator", "true")
							.toBoolean()
					enableWindowDecorations =
						settingsProperties.getProperty("enableWindowDecorations", "true")
							.toBoolean()
					enableWindowBorders =
						settingsProperties.getProperty("enableWindowBorders", "false").toBoolean()
					PomoDoro.pomoDoroSettings.workingDuration =
						settingsProperties.getProperty("workingDuration", "25.0").toFloat()
					PomoDoro.pomoDoroSettings.shortBreakDuration =
						settingsProperties.getProperty("shortBreakDuration", "5.0").toFloat()
					PomoDoro.pomoDoroSettings.longBreakDuration =
						settingsProperties.getProperty("longBreakDuration", "20.0").toFloat()
					PomoDoro.pomoDoroSettings.workSessionDuration =
						settingsProperties.getProperty("workSessionDuration", "4").toInt()
				}
			} catch (e: Exception) {
				e.printStackTrace()
			}
		}
	}

	fun setTheme(mode: ThemeMode) {
		themeMode = mode
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
					"themeMode",
					themeMode.name
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
				settingsProperties.setProperty("workingDuration", PomoDoro.pomoDoroSettings.workingDuration.toString())
				settingsProperties.setProperty(
					"shortBreakDuration",
					PomoDoro.pomoDoroSettings.shortBreakDuration.toString()
				)
				settingsProperties.setProperty(
					"longBreakDuration",
					PomoDoro.pomoDoroSettings.longBreakDuration.toString()
				)
				settingsProperties.setProperty(
					"workSessionDuration",
					PomoDoro.pomoDoroSettings.workSessionDuration.toString()
				)
				settingsProperties.store(output, "Pomolin App Settings")
			}
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	init {
		loadSettings()
		PomoDoro.setRoutine(PomoDoro.currentRoutine)
		if (enableWindowDecorations) {
			enableWindowBorders = false
		}
	}
}