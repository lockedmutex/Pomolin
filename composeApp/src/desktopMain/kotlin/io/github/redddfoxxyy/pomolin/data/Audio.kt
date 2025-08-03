package io.github.redddfoxxyy.pomolin.data

import javazoom.jlgui.basicplayer.BasicPlayer
import pomolin.composeapp.generated.resources.Res
import java.net.URI
import java.net.URL

internal class Audio {
    //	private val startPlayer = BasicPlayer()
    private val completionPlayer = BasicPlayer()

    //	private var startAudioFile: URL? = null
    private var completionAudioFile: URL? = null
    private fun loadAudioResources() {
//		startAudioFile = URI(Res.getUri("files/timerStart.mp3")).toURL()
        completionAudioFile = URI(Res.getUri("files/timerComplete.mp3")).toURL()
    }

    init {
        loadAudioResources()
//		startPlayer.open(startAudioFile)
        completionPlayer.open(completionAudioFile)
    }

//	internal fun playStartSound() {
//		try {
//			startPlayer.play()
//		} catch (e: Exception) {
//			println("Start audio playback failed: ${e.message}")
//			java.awt.Toolkit.getDefaultToolkit().beep()
//		}
//	}

    internal fun playCompletionSound() {
        try {
            completionPlayer.play()
        } catch (e: Exception) {
            println("Completion audio playback failed: ${e.message}")
            java.awt.Toolkit.getDefaultToolkit().beep()
        }
    }
}