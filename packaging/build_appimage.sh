#!/bin/bash
#
# Script to build an AppImage for Pomolin from the Gradle distributable.
# USAGE: Place this script inside the '.../build/compose/binaries/main-release'
#        directory (next to the 'app' folder) and run it.
#

# Exit immediately if a command exits with a non-zero status.
set -e

# --- Configuration ---
APP_NAME="pomolin"
APPDIR_NAME="$APP_NAME.AppDir"
GRADLE_APP_DIR="app/$APP_NAME" # Path relative to the current directory

# --- 1. Detect Architecture and Get AppImageTool ---
echo "--- Detecting Architecture ---"
TOOL_ARCH=$(uname -m)
TOOL_FILENAME="appimagetool-${TOOL_ARCH}.AppImage"
TOOL_URL="https://github.com/AppImage/AppImageKit/releases/download/continuous/${TOOL_FILENAME}"

if [ ! -f "$TOOL_FILENAME" ]; then
    echo "--- Downloading $TOOL_FILENAME ---"
    wget -q "$TOOL_URL"
    chmod +x "$TOOL_FILENAME"
else
    echo "--- Using existing $TOOL_FILENAME ---"
fi

# --- 2. Create the AppDir Structure ---
echo "--- Preparing AppDir ---"
# Clean up any previous build
rm -rf "$APPDIR_NAME"

mkdir -p "$APPDIR_NAME/usr/lib"
echo "Moving $GRADLE_APP_DIR to $APPDIR_NAME/usr/lib/"
mv "$GRADLE_APP_DIR" "$APPDIR_NAME/usr/lib/"

# --- 3. Create Desktop Entry and Icon ---
echo "--- Creating .desktop file and icon ---"
cat <<EOF > "$APPDIR_NAME/$APP_NAME.desktop"
[Desktop Entry]
Name=pomolin
Comment=A simple Pomodoro App written in Kotlin. Focus on what matters!
Exec=pomolin
Icon=pomolin
Terminal=false
Type=Application
Categories=Utility
EOF

# Copy icon to the standard path for desktop integration
ICON_STD_PATH="$APPDIR_NAME/usr/share/icons/hicolor/512x512/apps"
mkdir -p "$ICON_STD_PATH"
cp "$APPDIR_NAME/usr/lib/$APP_NAME/lib/$APP_NAME.png" "$ICON_STD_PATH/$APP_NAME.png"

# <--- ADDED THIS LINE: Also copy icon to the root for appimagetool to find
cp "$APPDIR_NAME/usr/lib/$APP_NAME/lib/$APP_NAME.png" "$APPDIR_NAME/$APP_NAME.png"

# --- 4. Create the AppRun Script ---
echo "--- Creating AppRun script ---"
cat <<EOF > "$APPDIR_NAME/AppRun"
#!/bin/sh
exec "\$APPDIR/usr/lib/$APP_NAME/bin/$APP_NAME" "\$@"
EOF
chmod +x "$APPDIR_NAME/AppRun"

# --- 5. Build the AppImage ---
echo "--- Building AppImage ---"
./"$TOOL_FILENAME" "$APPDIR_NAME"

# --- 6. Clean Up ---
echo "--- Cleaning up intermediate files ---"
rm -rf "$APPDIR_NAME"
# Note: We keep the downloaded appimagetool for future runs

echo ""
echo "✅ AppImage created successfully!"
echo "   Find your new app here: $(pwd)/${APP_NAME}-${TOOL_ARCH}.AppImage"
