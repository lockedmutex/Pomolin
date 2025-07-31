package com.redddfoxxyy.pomolin.handlers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

enum class PomoDoroRoutines(val displayName: String) {
	Working("Pomodoro"),
	ShortBreak("Short Break"),
	LongBreak("Long Break")
}

internal data class PomoDoroSettings(
	var workingDuration: Float = 25f,
	var shortBreakDuration: Float = 5f,
	var longBreakDuration: Float = 20f,
	var workSessionDuration: Int = 2
)


internal class PomoDoro {
	internal val appAudioManager = Audio()
	internal val currentTimer by mutableStateOf(Timer(25f, appAudioManager, ::progressToNextRoutine))

	internal val routineList = PomoDoroRoutines.entries.toList()
	internal var routineSettings by mutableStateOf(PomoDoroSettings())
	internal var currentRoutine by mutableStateOf(PomoDoroRoutines.Working)
	private var workSessionsCompleted = 0

	internal fun startTimer() = currentTimer.startTimer()
	internal fun pauseTimer() = currentTimer.pause()
	internal fun resetTimer() {
		currentTimer.reset()
	}

	private fun getDurationFor(routine: PomoDoroRoutines): Float = when (routine) {
		PomoDoroRoutines.Working -> routineSettings.workingDuration
		PomoDoroRoutines.ShortBreak -> routineSettings.shortBreakDuration
		PomoDoroRoutines.LongBreak -> routineSettings.longBreakDuration
	}

	internal fun setRoutine(routine: PomoDoroRoutines) {
		currentTimer.updateDuration(getDurationFor(routine))
		currentRoutine = routine
	}

	internal fun changeWorkingDuration(duration: Float) {
		val durationStepped = duration.toInt().toFloat()
		routineSettings = routineSettings.copy(workingDuration = durationStepped)
		if (currentRoutine == PomoDoroRoutines.Working) {
			currentTimer.updateDuration(durationStepped)
		}
	}

	internal fun changeShortBreakDuration(duration: Float) {
		val durationStepped = duration.toInt().toFloat()
		routineSettings = routineSettings.copy(shortBreakDuration = durationStepped)
		if (currentRoutine == PomoDoroRoutines.ShortBreak) {
			currentTimer.updateDuration(durationStepped)
		}
	}

	internal fun changeLongBreakDuration(duration: Float) {
		val durationStepped = duration.toInt().toFloat()
		routineSettings = routineSettings.copy(longBreakDuration = durationStepped)
		if (currentRoutine == PomoDoroRoutines.LongBreak) {
			currentTimer.updateDuration(durationStepped)
		}
	}

	internal fun changeWorkSessionDuration(duration: Float) {
		val durationStepped = duration.toInt()
		routineSettings = routineSettings.copy(workSessionDuration = durationStepped)
	}

	private fun progressToNextRoutine() {
		when (currentRoutine) {
			PomoDoroRoutines.Working -> {
				workSessionsCompleted++
				if (workSessionsCompleted >= routineSettings.workSessionDuration) {
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