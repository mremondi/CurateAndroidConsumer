# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


### Super important for firebase and likely other libraries
-keepclassmembers class curatetechnologies.com.curate_consumer.domain.model.** {
  *;
}
-keepclassmembers class curatetechnologies.com.curate_consumer.network.model.** {
  *;
}

-keepclassmembers class curatetechnologies.com.curate_consumer.network.firebase.model.** {
  *;
}



###---------------Begin: proguard configuration for ButterKnife  ----------
# For Butterknife:
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**

# Version 7
-keep class **$$ViewBinder { *; }
# Version 8
-keep class **_ViewBinding { *; }

-keepclasseswithmembernames class * { @butterknife.* <fields>; }
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
###---------------End: proguard configuration for ButterKnife  ----------


###---------------Begin: proguard configuration for Proguard  ----------
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions
###---------------End: proguard configuration for Proguard  ----------

###---------------Begin: proguard configuration for Okio  ----------
-dontwarn okio.**
###---------------End: proguard configuration for Okio  ----------


##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

##---------------End: proguard configuration for Gson  ----------

##---------------Begin: proguard configuration for Glide  ----------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

##---------------End: proguard configuration for Glide  ----------

##---------------Begin: proguard configuration for Stripe  ----------
-keep class com.stripe.android.** { *; }
##---------------End: proguard configuration for Stripe  ----------
