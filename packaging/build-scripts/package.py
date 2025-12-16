#!/usr/bin/env python3

import os
import platform
import subprocess
import shutil
import urllib.request
from pathlib import Path

# --- Configuration and Constants ---
WORKSPACE_PATH = Path(os.environ.get("WORKSPACE_PATH", Path.cwd()))
ARTIFACT_DIR = WORKSPACE_PATH / "composeApp/build/compose/binaries/main-release"
APP_NAME = "pomolin"
APPDIR_NAME = f"{APP_NAME}.AppDir"
GRADLE_APP_DIR = ARTIFACT_DIR / "app"
SOURCE_POMOLIN_DIR = GRADLE_APP_DIR / APP_NAME
PACKAGING_DIR = WORKSPACE_PATH / "packaging"

# --- Utility Functions ---

def run_command(command, check=True, cwd=None):
    """Executes a shell command and optionally checks for errors."""
    print(f"Executing: {' '.join(command)}")
    try:
        subprocess.run(
            command,
            check=check,
            cwd=cwd,
            stdout=subprocess.PIPE,
            stderr=subprocess.STDOUT,
            text=True
        )
    except subprocess.CalledProcessError as e:
        print(f"Error executing command: {e.cmd}")
        print(f"Output:\n{e.output}")
        if check:
            raise
    except FileNotFoundError as e:
        print(f"Error: Command not found - {command[0]}")
        if check:
            raise

def install_dependencies():
    """Updates the container and installs required dependencies."""
    print("Updating container and installing dependencies...")
    run_command(["dnf", "update", "-y"])
    run_command(["dnf", "install", "-y", "curl", "wget", "git", "ca-certificates",
                 "fuse-libs", "unzip", "binutils", "file", "glib2", "findutils"])

def install_java():
    """Installs Java 25 based on system architecture."""
    print("Installing Java 25...")
    arch = platform.machine()
    java_install_dir = Path("/usr/lib/jvm/temurin25")
    java_install_dir.mkdir(parents=True, exist_ok=True)
    tmp_dir = Path("/tmp")
    tmp_dir.mkdir(parents=True, exist_ok=True)

    java_url_base = "https://github.com/adoptium/temurin21-binaries/releases/download/jdk-21.0.9%2B10/"
    if arch == "x86_64":
        filename = "OpenJDK21U-jdk_x64_linux_hotspot_21.0.9_10.tar.gz"
    elif arch == "aarch64":
        filename = "OpenJDK21U-jdk_aarch64_linux_hotspot_21.0.9_10.tar.gz"
    else:
        raise RuntimeError(f"Unsupported architecture: {arch}")

    url = java_url_base + filename
    download_path = tmp_dir / filename

    print(f"Downloading {url}...")
    urllib.request.urlretrieve(url, download_path)

    print("Extracting Java...")
    run_command(["tar", "-xzf", str(download_path), "-C", str(tmp_dir)])

    extracted_dir = next(tmp_dir.glob("jdk-*"), None)
    if not extracted_dir:
        raise FileNotFoundError("Could not find extracted Java directory.")

    print(f"Moving {extracted_dir.name} to {java_install_dir}...")
    for item in extracted_dir.iterdir():
        shutil.move(str(item), str(java_install_dir / item.name))

    os.environ["JAVA_HOME"] = str(java_install_dir)
    os.environ["PATH"] = f"{java_install_dir / 'bin'}:{os.environ['PATH']}"

    print("Checking Java version...")
    run_command(["java", "-version"])

def run_gradle_build():
    """Sets up gradlew and runs the build command."""
    print(f"Changing directory to {WORKSPACE_PATH}")
    os.chdir(WORKSPACE_PATH)

    gradlew_path = WORKSPACE_PATH / "gradlew"
    print(f"Checking gradlew file at: {gradlew_path}")

    if not gradlew_path.exists():
        raise FileNotFoundError(f"gradlew not found at {gradlew_path}")

    os.chmod(gradlew_path, 0o755)

    print("Running Gradle createReleaseDistributable...")
    run_command(["./gradlew", "createReleaseDistributable"])

def create_tar_archive():
    """Creates the tar.gz archive of the Linux distributable."""
    print("Creating tar.gz archive for the Linux distributable...")
    if not SOURCE_POMOLIN_DIR.is_dir():
        raise FileNotFoundError(
            f"Error: The expected directory {SOURCE_POMOLIN_DIR} was not found after gradle build."
        )

    tar_output_dir = ARTIFACT_DIR / "tarball"
    tar_output_dir.mkdir(parents=True, exist_ok=True)
    arch = platform.machine()
    tar_filename = f"{APP_NAME}_{arch}_linux.tar.gz"
    tar_filepath = tar_output_dir / tar_filename

    print(f"Archiving '{SOURCE_POMOLIN_DIR}' to '{tar_filepath}'")
    # tar -czf [output_file] -C [base_dir] [dir_to_archive]
    run_command([
        "tar", "-czf", str(tar_filepath),
        "-C", str(GRADLE_APP_DIR),
        APP_NAME
    ])
    print("tar.gz archive created successfully.")

def download_and_setup_appimagetool():
    """Downloads and extracts appimagetool."""
    arch = platform.machine()
    if arch == "x86_64":
        tool_arch = "x86_64"
    elif arch == "aarch64":
        tool_arch = "aarch64"
    else:
        raise RuntimeError(f"Unsupported architecture for AppImageTool: {arch}")

    tool_filename = f"appimagetool-{tool_arch}.AppImage"
    tool_url = f"https://github.com/AppImage/AppImageKit/releases/download/continuous/{tool_filename}"
    tool_path = Path("/usr/local/bin/appimagetool")

    print(f"Downloading {tool_filename}...")
    download_filepath = WORKSPACE_PATH / tool_filename

    # Download tool
    urllib.request.urlretrieve(tool_url, download_filepath)

    # Make executable and extract
    os.chmod(download_filepath, 0o755)
    run_command([str(download_filepath), "--appimage-extract"])

    # Move extracted contents to target path
    extracted_path = WORKSPACE_PATH / "squashfs-root"
    if tool_path.exists():
        shutil.rmtree(tool_path)

    print(f"Moving {extracted_path.name} to {tool_path}...")
    shutil.move(str(extracted_path), str(tool_path))

    print("AppImageTool setup complete.")

def build_appdir_and_appimage():
    """Creates the AppDir structure and builds the final AppImage."""
    #
    print(f"Creating {APPDIR_NAME}...")
    appdir_path = WORKSPACE_PATH / APPDIR_NAME
    if appdir_path.exists():
        shutil.rmtree(appdir_path)

    # Base structure
    (appdir_path / "usr/lib").mkdir(parents=True)

    # Move application files
    print(f"Moving application distributable from {GRADLE_APP_DIR} to {appdir_path / 'usr/lib'}")

    # Check for the nested pomolin directory
    if (GRADLE_APP_DIR / APP_NAME).is_dir():
        print(f"Found nested {APP_NAME} directory, moving it correctly...")
        shutil.move(str(GRADLE_APP_DIR / APP_NAME), str(appdir_path / "usr/lib" / APP_NAME))
    elif GRADLE_APP_DIR.is_dir():
        print("Moving entire app directory as nested pomolin...")
        shutil.move(str(GRADLE_APP_DIR), str(appdir_path / "usr/lib" / APP_NAME))
    else:
        raise FileNotFoundError(f"Application directory not found: {GRADLE_APP_DIR}")


    print("Checking final structure:")
    run_command(["ls", "-la", str(appdir_path / "usr/lib" / APP_NAME)])

    # Copy .desktop file
    print("Copying .desktop file...")
    shutil.copy(PACKAGING_DIR / "appimage/pomolin.desktop", appdir_path)

    # Copy icon
    print("Copying icon...")
    icon_std_path = appdir_path / "usr/share/icons/hicolor/512x512/apps"
    icon_std_path.mkdir(parents=True)
    icon_source_path = WORKSPACE_PATH / "composeApp/src/desktopMain/composeResources/drawable/Pomolin.png"

    if not icon_source_path.is_file():
        raise FileNotFoundError(f"Icon not found at expected location: {icon_source_path}")

    shutil.copy(str(icon_source_path), str(icon_std_path / f"{APP_NAME}.png"))
    shutil.copy(str(icon_source_path), str(appdir_path / f"{APP_NAME}.png"))

    # Copy AppRun script
    print("Copying AppRun script...")
    apprun_path = appdir_path / "AppRun"
    shutil.copy(PACKAGING_DIR / "appimage/AppRun", apprun_path)
    os.chmod(apprun_path, 0o755)

    if not apprun_path.is_file():
        raise RuntimeError("AppRun script was not created!")
    #

    # Add update information
    print("Adding update information...")
    arch = platform.machine()
    update_info_x64 = "gh-releases-zsync|RedddFoxxyy|Pomolin|latest|pomolin-x86_64.AppImage.zsync"
    update_info_arm64 = "gh-releases-zsync|RedddFoxxyy|Pomolin|latest|pomolin-aarch64.AppImage.zsync"

    update_info_content = update_info_x64 if arch == "x86_64" else update_info_arm64
    with open(appdir_path / ".upd_info", "w") as f:
        f.write(update_info_content + "\n")

    # Run the AppImageTool
    print("Building AppImage with appimagetool...")
    appimage_output_dir = ARTIFACT_DIR / "appimage"
    appimage_output_dir.mkdir(parents=True, exist_ok=True)
    appimagetool_run = Path("/usr/local/bin/appimagetool/AppRun")

    run_command([
        str(appimagetool_run),
        str(appdir_path),
        "--sign",
        "--comp", "gzip"
    ])

    # Move the final AppImage and zsync file
    print("Moving final AppImage to artifacts directory...")
    for item in WORKSPACE_PATH.glob("Pomolin-*.AppImage"):
        shutil.move(str(item), str(appimage_output_dir / item.name))

    for item in WORKSPACE_PATH.glob("Pomolin-*.AppImage.zsync"):
        try:
            shutil.move(str(item), str(appimage_output_dir / item.name))
        except FileNotFoundError:
            # The zsync file might not be created if signing fails or is not supported
            pass

    print("AppImage created successfully!")

# --- Main Execution ---

def main():
    try:
        install_dependencies()

        install_java()

        run_gradle_build()

        create_tar_archive()

        download_and_setup_appimagetool()

        build_appdir_and_appimage()

        print("\n*** Build script finished successfully! ***")

    except Exception as e:
        print(f"\n*** Build script failed: {e} ***")
        import sys
        sys.exit(1)

if __name__ == "__main__":
    main()