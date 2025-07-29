<p align="center">
  <img src="composeApp/src/desktopMain/composeResources/drawable/Pomolin.png" alt="Pomolin app icon" width="200"/>
</p>



<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-2.2.0-7F52FF.svg?style=for-the-badge&logo=kotlin" alt="Kotlin badge"/>
  <img src="https://img.shields.io/badge/Jetpack%20Compose-1.8.2-4285F4.svg?style=for-the-badge&logo=jetpackcompose" alt="Jetpack Compose badge"/>
  <img src="https://img.shields.io/badge/Gradle-8.14.3-02303A.svg?style=for-the-badge&logo=gradle" alt="Gradle badge"/>
  <img src="https://img.shields.io/badge/pomodoro-technique-ff4500.svg?style=for-the-badge" alt="Pomodoro badge"/>
</p>

<h1 align="center">Pomolin</h1>

<p align="center">
  <img src="gitAssets/pomolin.avif" alt="Pomolin app icon" width="450"/>
</p>

A simple, beautiful, and minimalist Pomodoro timer for your desktop. Designed to help you stay
focused and productive, it provides an elegant and straightforward way to manage your work and break
intervals.

***

## Features

* **Animated Timer:** The timer digits animate smoothly, offering a dynamic and visually
  pleasing experience.
* **Interactive Controls:**
	* The Play/Pause button animates to clearly indicate the timer's state.
	* A satisfying rotation animation plays on the reset button when clicked.
* **Routine Management:** Switch between **Pomodoro (25 min)**, **Short Break (5
  min)**, and **Long Break (20 min)** routines.
* **Audio Notifications:** The app plays a sound when the timer completes.
* **Cross-Platform:** Native installers are provided for Windows, macOS, and Linux on
  both `x64` and `arm64` architectures.

***

## Download & Run

You can download the latest version of Pomolin from the
**[Releases](https://github.com/RedddFoxxyy/pomolin/releases)** page.

### Windows

1. Download the `.msi` or `.exe` file.
2. Run the installer and follow the on-screen instructions.

### macOS

1. Download the `.dmg` or `.pkg` file.
2. Open the file and drag the `Pomolin.app` to your `Applications` folder.

> NOTE: For macOS, it will not allow you to run the .pkg or the app installed using .dmg, to do so you will have to go
> to privacy and security in settings, and then allow the execution of the app.

### Linux

1. Download the `.deb` or `.AppImage` file.
2. Install `deb` using your package manager, for example:
   ```bash
   sudo apt install ./pomolin_1.0.2_amd64.deb
   ```
   or Install `AppImage` using `AppImageLauncher` or equivalent. You can run the AppImage just by double-clicking on it
   too, no need to install it.

   > NOTE: AppImage is the preferred way to run the app on Linux-based distributions.

### JVM

1. Download the `.jar` file for your OS and CPU.
2. Run it using JRE24 or JDK24:
   ```bash
   java -jar pomolin-macos-arm64-1.0.2-release.jar 
   ```
   > NOTE: Make sure you use java24 only to run the jars. (Java21 on Windows Arm64).
   >
   > Preferably use Temurin or ZULU JRE/JDK.

***

## Building from Source

To build Pomolin from the source code, you'll need to have **JDK** and **Git** installed.

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/RedddFoxxyy/pomolin.git](https://github.com/RedddFoxxyy/pomolin.git)
   cd pomolin
   ```

2. **Build the application using Gradle:**

	* On **Linux**:
	    ```bash
		./gradlew packageReleaseDeb
		```
	* On **macOS**:
	    ```bash
		./gradlew packageReleaseDmg
		```
	* On **Windows**:
	    ```bash
		.\gradlew.bat packageReleaseMsi
		```

3. The compiled application will be available in the `composeApp/build/compose/binaries/` directory.

***

## License

This project is licensed under the **GNU Affero General Public License v3.0**. See
the [LICENSE](LICENSE) file for details.