package com.redddfoxxyy.pomolin.handlers

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow

internal class Timer(durationMinutes: Float, val audio: Audio, private val onFinished: () -> Unit) {
	private var coroutineScope = CoroutineScope(Dispatchers.Main)

	// Timer Handlers
	private var initialTimeMillis = (durationMinutes * 60 * 1000L).toLong()
	private var remainingTimeMillis = initialTimeMillis
	internal val formatedTime = mutableStateOf(formatTime(initialTimeMillis))
	private var lastUpdateTime = 0L
	internal val isTimerRunning = MutableStateFlow(false)
	private var completionAudioPlayed = false

	internal fun startTimer() {
		if (isTimerRunning.value || remainingTimeMillis <= 0L) return

		coroutineScope.launch {
			lastUpdateTime = System.currentTimeMillis()
			isTimerRunning.value = true

			while (isTimerRunning.value && remainingTimeMillis > 0) {
				delay(50L)
				val elapsed = System.currentTimeMillis() - lastUpdateTime
				remainingTimeMillis -= elapsed
				lastUpdateTime = System.currentTimeMillis()

				if (remainingTimeMillis < 0L) {
					remainingTimeMillis = 0L
				}

				if (remainingTimeMillis <= 1800L && !completionAudioPlayed) {
					audio.playCompletionSound()
					completionAudioPlayed = true
				}

				formatedTime.value = formatTime(remainingTimeMillis)
			}

			if (remainingTimeMillis <= 0L) {
				isTimerRunning.value = false
				completionAudioPlayed = false
				onFinished()
			}
		}
	}

	internal fun pause() {
		isTimerRunning.value = false
	}

	internal fun reset() {
		isTimerRunning.value = false
		remainingTimeMillis = initialTimeMillis
		lastUpdateTime = 0L
		formatedTime.value = formatTime(initialTimeMillis)
		completionAudioPlayed = false
		coroutineScope.cancel()
		coroutineScope = CoroutineScope(Dispatchers.Main)
	}

	internal fun updateDuration(durationMinutes: Float) {
		reset()
		initialTimeMillis = (durationMinutes * 60 * 1000L).toLong()
		remainingTimeMillis = initialTimeMillis
		formatedTime.value = formatTime(initialTimeMillis)
	}

	private fun formatTime(millis: Long): String {
		val totalSeconds = millis / 1000
		val minutes = (totalSeconds / 60)
		val seconds = totalSeconds % 60
		return String.format("%02d:%02d", minutes, seconds)
	}
}
