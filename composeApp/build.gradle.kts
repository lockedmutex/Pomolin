import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
	alias(libs.plugins.kotlinMultiplatform)
	alias(libs.plugins.composeMultiplatform)
	alias(libs.plugins.composeCompiler)
	alias(libs.plugins.composeHotReload)
}

kotlin {
	jvm("desktop")

    jvmToolchain(21)

	sourceSets {
		val desktopMain by getting

		commonMain.dependencies {
			implementation(compose.runtime)
			implementation(compose.foundation)
			implementation(compose.material3)
			implementation(compose.ui)
			implementation(compose.components.resources)
			implementation(compose.components.uiToolingPreview)
		}
		commonTest.dependencies {
			implementation(libs.kotlin.test)
		}
		desktopMain.dependencies {
			implementation(compose.desktop.currentOs)
            implementation(compose.components.resources)
            implementation(compose.animation)
			implementation(libs.kotlinx.coroutinesSwing)
			implementation(libs.soundlibs.basicplayer)
            implementation(libs.soundlibs.mp3spi)
			implementation(libs.logback.classic)
			implementation(libs.commons.lang3)
			implementation(libs.platformtools.darkmodedetector)
			implementation(libs.dirs.directories)
		}
	}
}

compose.desktop {
	application {
		mainClass = "io.github.redddfoxxyy.pomolin.MainKt"
		nativeDistributions {
			targetFormats(
				TargetFormat.Dmg,
				TargetFormat.Pkg,
				TargetFormat.Exe,
				TargetFormat.Msi,
				TargetFormat.Deb,
				TargetFormat.Rpm
			)
			packageName = "pomolin"
			packageVersion = "1.2.0"
			description = "A simple Pomodoro App written in Kotlin. Focus on what matters! "
			vendor = "Suyog Tandel"
			licenseFile.set(project.file("../LICENSE"))

			linux {
				iconFile.set(project.file("src/desktopMain/composeResources/drawable/Pomolin.png"))
				packageName = "pomolin"
				debMaintainer = "Suyog Tandel"
				appCategory = "Utility"
			}
			macOS {
				packageName = "pomolin"
				bundleID = "io.github.redddfoxxyy.pomolin"
				iconFile.set(
					project.file(
						"src/desktopMain/composeResources/drawable/PomolinMac" +
								".icns"
					)
				)
				dockName = "Pomolin"
				copyright = "© 2025 RedddFoxxyy. All rights reserved."
			}
			windows {
				packageName = "pomolin"
				iconFile.set(
					project.file(
						"src/desktopMain/composeResources/drawable/PomolinWin" +
								".ico"
					)
				)
				console = false
				menu = true
				shortcut = true
				copyright = "© 2025 RedddFoxxyy. All rights reserved."
			}
		}
		jvmArgs += listOf(
			"-Xms64m",
			"-Xmx128m",
			"-XX:MinHeapFreeRatio=10",
			"-XX:MaxHeapFreeRatio=10",
			"-XX:+UseG1GC",
			"-XX:MaxGCPauseMillis=100",
			"-XX:+UseStringDeduplication",
			"-XX:+PerfDisableSharedMem",
//			"--enable-native-access=ALL-UNNAMED",
//			"--illegal-native-access=deny"
		)

		buildTypes.release.proguard {
            isEnabled.set(false)
            obfuscate.set(false)
            optimize.set(false)
		}
	}
}
