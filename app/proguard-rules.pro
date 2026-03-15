# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see:
# http://developer.android.com/guide/developing/tools/proguard.html

# Keep Application class
-keep class br.com.lucaslima.cryptogram.CryptogramApplication { *; }

# Keep all Activities
-keep class * extends android.app.Activity
-keep class * extends androidx.appcompat.app.AppCompatActivity

# Keep all Fragments
-keep class * extends androidx.fragment.app.Fragment

# Keep all ViewModels
-keep class * extends androidx.lifecycle.ViewModel

# Keep Java records (Java 16+)
-keepclassmembers class * {
    ** $values;
    ** $VALUES;
}

# AndroidX Navigation
-keep class androidx.navigation.** { *; }

# Prevent stripping of interface implementations
-keepclassmembers class * implements java.io.Serializable {
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Remove verbose logging in release builds
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int d(...);
    public static int i(...);
}
