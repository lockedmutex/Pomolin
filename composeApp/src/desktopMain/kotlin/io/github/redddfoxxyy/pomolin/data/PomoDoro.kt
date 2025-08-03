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
    var enableProgressIndicator by mutableStateOf(true)
}


internal class PomoDoro {
    internal val appAudioManager = Audio()
    internal val timerInstance = Timer(25f, appAudioManager, ::progressToNextRoutine)

    internal val routineList = PomoDoroRoutines.entries.toList()
    internal var appSettings = PomoDoroSettings()
    internal var currentRoutine by mutableStateOf(PomoDoroRoutines.Working)
    internal var workSessionsCompleted by mutableStateOf(0)

    internal fun startTimer() = timerInstance.startTimer()
    internal fun pauseTimer() = timerInstance.pause()
    internal fun resetTimer() {
        timerInstance.reset()
    }

    private fun getDurationFor(routine: PomoDoroRoutines): Float = when (routine) {
        PomoDoroRoutines.Working -> appSettings.workingDuration
        PomoDoroRoutines.ShortBreak -> appSettings.shortBreakDuration
        PomoDoroRoutines.LongBreak -> appSettings.longBreakDuration
    }

    internal fun setRoutine(routine: PomoDoroRoutines) {
        timerInstance.updateDuration(getDurationFor(routine))
        currentRoutine = routine
    }

    internal fun changeWorkingDuration(duration: Float) {
        val durationStepped = duration.toInt().toFloat()
        appSettings.workingDuration = durationStepped
        if (currentRoutine == PomoDoroRoutines.Working) {
            timerInstance.updateDuration(durationStepped)
        }
    }

    internal fun changeShortBreakDuration(duration: Float) {
        val durationStepped = duration.toInt().toFloat()
        appSettings.shortBreakDuration = durationStepped
        if (currentRoutine == PomoDoroRoutines.ShortBreak) {
            timerInstance.updateDuration(durationStepped)
        }
    }

    internal fun changeLongBreakDuration(duration: Float) {
        val durationStepped = duration.toInt().toFloat()
        appSettings.longBreakDuration = durationStepped
        if (currentRoutine == PomoDoroRoutines.LongBreak) {
            timerInstance.updateDuration(durationStepped)
        }
    }

    internal fun changeWorkSessionDuration(duration: Float) {
        val durationStepped = duration.toInt()
        appSettings.workSessionDuration = durationStepped
    }

    private fun progressToNextRoutine() {
        when (currentRoutine) {
            PomoDoroRoutines.Working -> {
                workSessionsCompleted++
                if (workSessionsCompleted >= appSettings.workSessionDuration) {
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