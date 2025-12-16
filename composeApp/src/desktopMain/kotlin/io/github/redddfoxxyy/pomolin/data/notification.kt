package io.github.redddfoxxyy.pomolin.data

import java.util.Locale

object Notif {

    fun sendNotification(title: String, message: String) {
        val os = System.getProperty("os.name", "generic").lowercase(Locale.ENGLISH)

        try {
            when {
                os.contains("mac") -> {
                    val processBuilder = ProcessBuilder(
                        "osascript", "-e",
                        "display notification \"$message\" with title \"$title\""
                    )
                    processBuilder.start()
                }

                os.contains("win") -> {
                    val script = """
                        [Windows.UI.Notifications.ToastNotificationManager, Windows.UI.Notifications, ContentType = WindowsRuntime] | Out-Null;
                        ${'$'}template = [Windows.UI.Notifications.ToastNotificationManager]::GetTemplateContent([Windows.UI.Notifications.ToastTemplateType]::ToastText02);
                        ${'$'}text = ${'$'}template.GetElementsByTagName('text');
                        ${'$'}text[0].AppendChild(${'$'}template.CreateTextNode('$title'));
                        ${'$'}text[1].AppendChild(${'$'}template.CreateTextNode('$message'));
                        ${'$'}notifier = [Windows.UI.Notifications.ToastNotificationManager]::CreateToastNotifier('Pomolin');
                        ${'$'}notifier.Show([Windows.UI.Notifications.ToastNotification]::new(${'$'}template));
                    """.trimIndent().replace("\n", " ")

                    ProcessBuilder("powershell", "-NoProfile", "-Command", script).start()
                }

                os.contains("nux") || os.contains("nix") -> {
                    val processBuilder = ProcessBuilder("notify-send", title, message)
                    processBuilder.start()
                }

                else -> {
                    println("Notifications not supported on this OS without a SystemTray icon.")
                }
            }
        } catch (e: Exception) {
            println("Failed to send notification: ${e.message}")
            e.printStackTrace()
        }
    }
}