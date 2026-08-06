# This is a configuration file for ProGuard.
# http://proguard.sourceforge.net/index.html#manual/usage.html

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile

# Kotlin
-keep class kotlin.Metadata { *; }
-dontwarn kotlin.**
-keepclassmembers class **$WhenMappings {
    <fields>;
}

# Coroutines
-keep class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.coroutines.**

# AndroidX
-keep class androidx.** { *; }
-dontwarn androidx.**

# Room Database
-keep class androidx.room.** { *; }
-keep @androidx.room.Entity class * { *; }
-dontwarn androidx.room.**

# DataStore
-keep class androidx.datastore.** { *; }
-dontwarn androidx.datastore.**

# Material Design
-keep class com.google.android.material.** { *; }
-dontwarn com.google.android.material.**

# GSON
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**
-keepattributes EnclosingMethod
-keepattributes InnerClasses

# App specific
-keep class com.tvbox.kmmapper.** { *; }
-keepclassmembers class com.tvbox.kmmapper.data.models.** { *; }
-keepclassmembers class com.tvbox.kmmapper.data.dao.** { *; }

# Mantener constructores públicos/privados
-keepclassmembers class * {
    public <init>(...);
}
