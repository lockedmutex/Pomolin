package io.github.redddfoxxyy.pomolin.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class Timer(durationMinutes: Float, val audio: Audio, private val onFinished: () -> Unit) {

	internal var isTimerRunning by mutableStateOf(false)
	private var initialTimeMillis = (durationMinutes * 60 * 1000L).toLong()
	private var remainingTimeMillis = initialTimeMillis
	private var timerProgress = 1.0f
	internal var formatedTime by mutableStateOf(formatTime(initialTimeMillis))
	private var lastUpdateTime = 0L

	private var coroutineScope = CoroutineScope(Dispatchers.Main)

	private var completionAudioPlayed = false

	internal fun startTimer() {
		if (isTimerRunning || remainingTimeMillis <= 0L) return

		coroutineScope.launch {
			lastUpdateTime = System.currentTimeMillis()
			isTimerRunning = true

			while (isTimerRunning && remainingTimeMillis > 0) {
				delay(50L)
				val elapsed = System.currentTimeMillis() - lastUpdateTime
				remainingTimeMillis -= elapsed
				lastUpdateTime = System.currentTimeMillis()
				timerProgress = remainingTimeMillis.toFloat() / initialTimeMillis.toFloat()

				if (remainingTimeMillis < 0L) {
					remainingTimeMillis = 0L
				}

				if (remainingTimeMillis <= 1800L && !completionAudioPlayed) {
					audio.playCompletionSound()
					completionAudioPlayed = true
				}

				formatedTime = formatTime(remainingTimeMillis)
			}

			if (remainingTimeMillis <= 0L) {
				isTimerRunning = false
				completionAudioPlayed = false
				onFinished()
			}
		}
	}

	internal fun pause() {
		isTimerRunning = false
	}

	internal fun reset() {
		isTimerRunning = false
		remainingTimeMillis = initialTimeMillis
		lastUpdateTime = 0L
		formatedTime = formatTime(initialTimeMillis)
		timerProgress = 1.0f
		completionAudioPlayed = false
		coroutineScope.cancel()
		coroutineScope = CoroutineScope(Dispatchers.Main)
	}

	internal fun updateDuration(durationMinutes: Float) {
		reset()
		initialTimeMillis = (durationMinutes * 60 * 1000L).toLong()
		remainingTimeMillis = initialTimeMillis
		formatedTime = formatTime(initialTimeMillis)
	}

	internal fun getTimerProgress(): Float {
		return timerProgress
	}

	private fun formatTime(millis: Long): String {
		val totalSeconds = millis / 1000
		val minutes = (totalSeconds / 60)
		val seconds = totalSeconds % 60
		return String.format("%02d:%02d", minutes, seconds)
	}
}
