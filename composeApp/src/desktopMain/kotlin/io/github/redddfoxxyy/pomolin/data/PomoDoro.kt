package io.github.redddfoxxyy.pomolin.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

enum class PomoDoroRoutines(val displayName: String) {
	Working("Pomodoro"),
	ShortBreak("Short Break"),
	LongBreak("Long Break")
}

internal class PomoDoroSettings {
	var workingDuration by mutableFloatStateOf(25f)
	var shortBreakDuration by mutableFloatStateOf(5f)
	var longBreakDuration by mutableFloatStateOf(20f)
	var workSessionDuration by mutableStateOf(4)
}


internal object PomoDoro {
	internal val appAudioManager = Audio()
	internal val timerInstance = Timer(25f, appAudioManager, ::progressToNextRoutine)

	internal val routineList = PomoDoroRoutines.entries.toList()
	internal var pomoDoroSettings = PomoDoroSettings()
	internal var currentRoutine by mutableStateOf(PomoDoroRoutines.Working)
	internal var workSessionsCompleted by mutableStateOf(0)

	internal fun startTimer() = timerInstance.startTimer()
	internal fun pauseTimer() = timerInstance.pause()
	internal fun resetTimer() = timerInstance.reset()


	private fun getDurationFor(routine: PomoDoroRoutines): Float = when (routine) {
		PomoDoroRoutines.Working -> pomoDoroSettings.workingDuration
		PomoDoroRoutines.ShortBreak -> pomoDoroSettings.shortBreakDuration
		PomoDoroRoutines.LongBreak -> pomoDoroSettings.longBreakDuration
	}

	internal fun setRoutine(routine: PomoDoroRoutines) {
		timerInstance.updateDuration(getDurationFor(routine))
		currentRoutine = routine
	}

	internal fun changeWorkingDuration(duration: Float) {
		val durationStepped = duration.toInt().toFloat()
		pomoDoroSettings.workingDuration = durationStepped
		if (currentRoutine == PomoDoroRoutines.Working) {
			timerInstance.updateDuration(durationStepped)
		}
		AppSettings.saveSettings()
	}

	internal fun changeShortBreakDuration(duration: Float) {
		val durationStepped = duration.toInt().toFloat()
		pomoDoroSettings.shortBreakDuration = durationStepped
		if (currentRoutine == PomoDoroRoutines.ShortBreak) {
			timerInstance.updateDuration(durationStepped)
		}
		AppSettings.saveSettings()
	}

	internal fun changeLongBreakDuration(duration: Float) {
		val durationStepped = duration.toInt().toFloat()
		pomoDoroSettings.longBreakDuration = durationStepped
		if (currentRoutine == PomoDoroRoutines.LongBreak) {
			timerInstance.updateDuration(durationStepped)
		}
		AppSettings.saveSettings()
	}

	internal fun changeWorkSessionDuration(duration: Float) {
		val durationStepped = duration.toInt()
		pomoDoroSettings.workSessionDuration = durationStepped
		AppSettings.saveSettings()
	}

	private fun progressToNextRoutine() {
		when (currentRoutine) {
			PomoDoroRoutines.Working -> {
				workSessionsCompleted++
				if (workSessionsCompleted >= pomoDoroSettings.workSessionDuration) {
					setRoutine(PomoDoroRoutines.LongBreak)
					workSessionsCompleted = 0
				} else {
					setRoutine(PomoDoroRoutines.ShortBreak)
				}
			}

			PomoDoroRoutines.ShortBreak, PomoDoroRoutines.LongBreak -> {
				setRoutine(PomoDoroRoutines.Working)
			}
		}
	}
}