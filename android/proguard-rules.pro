# Konfigurasi dasar
-verbose
-keepattributes LineNumberTable,SourceFile
-renamesourcefileattribute SourceFile

# Ignore warnings
-dontwarn android.support.**
-dontwarn com.badlogic.gdx.backends.android.AndroidFragmentApplication

# LibGDX Core
-keep class com.badlogic.gdx.** { *; }
-keep class com.badlogic.gdx.graphics.** { *; }
-keep class com.badlogic.gdx.scenes.scene2d.** { *; }
-keep class com.badlogic.gdx.graphics.g2d.BitmapFont { *; }
-keep class com.badlogic.gdx.graphics.Color { *; }

# LibGDX Controllers
-keep class com.badlogic.gdx.controllers.android.AndroidControllers { *; }

# Box2D Physics
-keepclassmembers class com.badlogic.gdx.physics.box2d.World {
   boolean contactFilter(long, long);
   void    beginContact(long);
   void    endContact(long);
   void    preSolve(long, long);
   void    postSolve(long, long);
   boolean reportFixture(long);
   float   reportRayFixture(long, float, float, float, float, float);
}

# JSON Serialization
# Jaga semua class yang memiliki field 'regions'
-keepclassmembers class * {
    *** regions;
    <fields>;
}

# Jaga semua class model dalam package io.asirum
-keep class io.asirum.** { *; }
-keepclassmembers class io.asirum.** {
    <fields>;
    <methods>;
}

# Jaga class yang ter-obfuscate (t.a, p0.b dll)
-keep class t.** { *; }
-keep class p0.** { *; }

# Anotasi dan Signature
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes EnclosingMethod

# LibGDX JSON Serialization
-keep class com.badlogic.gdx.utils.Json { *; }
-keep class com.badlogic.gdx.utils.JsonValue { *; }
-keep class com.badlogic.gdx.utils.ObjectMap { *; }
-keep class com.badlogic.gdx.utils.Array { *; }

# Jaga semua constructor
-keepclassmembers class * {
    public <init>(...);
}

# Jaga field 'regions' di SEMUA class
-keepclassmembers class * {
    *** regions;
}

# Jaga semua class model yang terkait payload.json (misal package-mu io.asirum atau yang lain)
-keep class io.asirum.** { *; }
-keepclassmembers class io.asirum.** { *; }

# Jaga semua class yang kemungkinan terobfuscate
-keep class t.a { *; }
-keep class t.** { *; }
-keep class p0.** { *; }
