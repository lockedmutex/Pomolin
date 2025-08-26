#!/bin/bash

# Script to build AppImage and pack a tar.gz from application distributable on rocky linux for maximum glibc compatibility.
# CAUTION! Only run this on rocky linux.

arch=$(uname -m)

# Install dependencies
echo "Updating container and installing dependencies..."

dnf update -y
dnf install -y curl wget git ca-certificates fuse-libs unzip binutils file glib2 findutils
echo "Adding Azul Zulu repository..."
rpm --import https://repos.azul.com/azul-repo.key
curl -s https://repos.azul.com/zulu/zulu-rhel.repo -o /etc/yum.repos.d/zulu.repo

# Install Java 24 using manual download
echo "Installing Java 24..."

mkdir -p /usr/lib/jvm
cd /tmp || exit

if [[ "$arch" == x86_64 ]]; then
  wget https://cdn.azul.com/zulu/bin/zulu24.32.13-ca-jdk24.0.2-linux_x64.tar.gz
  tar -xzf zulu24.32.13-ca-jdk24.0.2-linux_x64.tar.gz
  mkdir -p /usr/lib/jvm/zulu24
  mv zulu24.32.13-ca-jdk24.0.2-linux_x64/* /usr/lib/jvm/zulu24/
else
  wget https://cdn.azul.com/zulu/bin/zulu24.32.13-ca-jdk24.0.2-linux_aarch64.tar.gz
  tar -xzf zulu24.32.13-ca-jdk24.0.2-linux_aarch64.tar.gz
  mkdir -p /usr/lib/jvm/zulu24
  mv zulu24.32.13-ca-jdk24.0.2-linux_aarch64/* /usr/lib/jvm/zulu24/
fi

echo "Setting JAVA_HOME and PATH..."
export JAVA_HOME=/usr/lib/jvm/zulu24
export PATH="$JAVA_HOME/bin:$PATH"
java -version

echo "Checking gradlew file..."
cd "${WORKSPACE_PATH:-$(pwd)}" || exit
echo "Current directory: $(pwd)"
ls -la ./gradlew
chmod +x ./gradlew

echo "Running Gradle createReleaseDistributable..."
./gradlew createReleaseDistributable

# Define paths and variables
ARTIFACT_DIR="composeApp/build/compose/binaries/main-release"
APP_NAME="pomolin"
APPDIR_NAME="$APP_NAME.AppDir"
GRADLE_APP_DIR="$ARTIFACT_DIR/app"
SOURCE_POMOLIN_DIR="$GRADLE_APP_DIR/pomolin"

echo "Creating tar.gz archive for the Linux distributable..."
if [ -d "$SOURCE_POMOLIN_DIR" ]; then
  TAR_OUTPUT_DIR="$ARTIFACT_DIR/tarball"
  mkdir -p "$TAR_OUTPUT_DIR"
  TAR_FILENAME="pomolin_${arch}_linux.tar.gz"
  echo "Archiving '$SOURCE_POMOLIN_DIR' to '$TAR_OUTPUT_DIR/$TAR_FILENAME'"
  tar -czf "$TAR_OUTPUT_DIR/$TAR_FILENAME" -C "$GRADLE_APP_DIR" "pomolin"
  echo "tar.gz archive created successfully."
else
  echo "Error: The expected directory $SOURCE_POMOLIN_DIR was not found after gradle build."
  exit 1
fi

# Dynamically select and download the correct appimagetool
if [[ "$arch" == x86_64 ]]; then
  TOOL_ARCH="x86_64"
else
  TOOL_ARCH="aarch64"
fi

TOOL_FILENAME="appimagetool-${TOOL_ARCH}.AppImage"
TOOL_URL="https://github.com/AppImage/AppImageKit/releases/download/continuous/${TOOL_FILENAME}"

echo "Downloading $TOOL_FILENAME..."
wget -q "$TOOL_URL"
chmod +x "$TOOL_FILENAME"
./"$TOOL_FILENAME" --appimage-extract && mv squashfs-root /usr/local/bin/appimagetool

# Create the AppDir structure
echo "Creating $APPDIR_NAME..."
mkdir -p "$APPDIR_NAME/usr/lib"
echo "Moving $GRADLE_APP_DIR to $APPDIR_NAME/usr/lib/"

# The app directory contains pomolin subdirectory, so we need to move the contents correctly
if [ -d "$GRADLE_APP_DIR/pomolin" ]; then
  echo "Found pomolin subdirectory, moving it correctly..."
  mv "$GRADLE_APP_DIR/pomolin" "$APPDIR_NAME/usr/lib/$APP_NAME"
else
  echo "Moving entire app directory as pomolin..."
  mv "$GRADLE_APP_DIR" "$APPDIR_NAME/usr/lib/$APP_NAME"
fi

echo "Checking final structure:"
ls -la "$APPDIR_NAME/usr/lib/$APP_NAME/"

# Add .desktop file
cp packaging/appimage/pomolin.desktop $APPDIR_NAME

# Copy the icon
echo "Copying icon..."
ICON_STD_PATH="$APPDIR_NAME/usr/share/icons/hicolor/512x512/apps"
mkdir -p "$ICON_STD_PATH"
ICON_PATH="composeApp/src/desktopMain/composeResources/drawable/Pomolin.png"
if [ -f "$ICON_PATH" ]; then
  echo "Found icon at: $ICON_PATH"
  cp "$ICON_PATH" "$ICON_STD_PATH/$APP_NAME.png"
  cp "$ICON_PATH" "$APPDIR_NAME/$APP_NAME.png"
else
  echo "Icon not found at expected location: $ICON_PATH"
  exit 1
fi

# Add the AppRun script
echo "Copying AppRun script..."
cp packaging/appimage/AppRun $APPDIR_NAME
chmod +x "$APPDIR_NAME/AppRun"
if [ ! -f "$APPDIR_NAME/AppRun" ]; then
  echo "ERROR: AppRun script was not created!"
  exit 1
fi

# Add update information
echo "Adding update information..."
UPDATE_INFO_X64="gh-releases-zsync|RedddFoxxyy|Pomolin|latest|pomolin-x86_64.AppImage.zsync"
UPDATE_INFO_ARM64="gh-releases-zsync|RedddFoxxyy|Pomolin|latest|pomolin-aarch64.AppImage.zsync"

if [[ "$arch" == x86_64 ]]; then
	echo "$UPDATE_INFO_X64" > "$APPDIR_NAME/.upd_info"
else
	echo "$UPDATE_INFO_ARM64" > "$APPDIR_NAME/.upd_info"
fi

# Run the AppImageTool
echo "Building AppImage with appimagetool..."
mkdir -p "$ARTIFACT_DIR/appimage"
/usr/local/bin/appimagetool/AppRun "$APPDIR_NAME" --sign --comp gzip

# Move the final AppImage to the artifacts directory for upload
mv Pomolin-*.AppImage "$ARTIFACT_DIR/appimage/"
mv Pomolin-*.AppImage.zsync "$ARTIFACT_DIR/appimage/" 2>/dev/null || true

echo "AppImage created successfully!"