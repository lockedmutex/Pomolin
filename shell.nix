{
  pkgs ? import <nixpkgs> { },
}:

let
  nativeDeps = with pkgs; [
    libx11
    libxext
    libxrender
    libxtst
    libxi
    freetype
    fontconfig
    libGL
    alsa-lib
    at-spi2-atk
    at-spi2-core
    cairo
    gdk-pixbuf
    glib
    gtk3
    pango
  ];
in

pkgs.mkShell {
  name = "pomolin-dev";

  packages =
    with pkgs;
    [
      javaPackages.compiler.temurin-bin.jdk-21
      # gradle
      # kotlin
    ]
    ++ nativeDeps;

  shellHook = ''
    		export JAVA_HOME="${pkgs.javaPackages.compiler.temurin-bin.jdk-21}"
    		export PATH="$JAVA_HOME/bin:$PATH"

    		export LD_LIBRARY_PATH="${pkgs.lib.makeLibraryPath nativeDeps}:$LD_LIBRARY_PATH"

    		echo "Pomolin dev shell ready — JDK $(java -version 2>&1 | head -1)"
    	'';
}
