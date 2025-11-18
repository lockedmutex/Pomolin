# --- Your Existing Rules ---
-keep class javazoom.jlgui.basicplayer.** { *; }
-keep class com.googlecode.soundlibs.** { *; }
-keep class * implements javax.sound.sampled.spi.AudioFileReader
-keep class * implements javax.sound.sampled.spi.FormatConversionProvider
-dontwarn javazoom.**
-dontwarn tritonus.**
-dontwarn javax.servlet.**
-dontwarn javax.mail.**
-dontwarn org.codehaus.janino.**
-dontwarn org.codehaus.commons.compiler.**
-dontwarn sun.reflect.**
-dontwarn jakarta.**
-dontwarn org.tukaani.xz.**

# --- Added/Modified Rules to Fix Warnings ---

# Keep all of SLF4J and Logback to prevent runtime errors from dynamic access.
# This is safer than keeping individual classes and will resolve the logback warnings.
-keep class org.slf4j.** { *; }
-keep class ch.qos.logback.** { *; }

# Suppress warnings for optional Logback components that are not used in your project.
-dontwarn ch.qos.logback.classic.servlet.**
-dontwarn ch.qos.logback.core.net.**

# Keep JUnit classes. Even though it's a testing library, ProGuard sees dynamic
# access and warns about it. This will fix the junit warnings.
-keep class junit.** { *; }
-dontwarn com.ibm.uvm.tools.DebugSupport

# Keep important attributes for stack traces and debugging
-keepattributes Signature,SourceFile,LineNumberTable,EnclosingMethod

# Keep main application class and Composable functions
-keep class io.github.redddfoxxyy.pomolin.MainKt {
    public static void main(java.lang.String[]);
}
-keepclasseswithmembers public class * {
    @androidx.compose.runtime.Composable <methods>;
}

# This rule helps with reflection issues in Kotlin
-keep class kotlin.reflect.jvm.internal.** { *; }