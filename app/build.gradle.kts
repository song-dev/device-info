import java.text.SimpleDateFormat
import java.util.Date

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.services)
}

val PRODUCT_NAME = "device_info_"
val DATE = SimpleDateFormat("yyyyMMdd").format(Date())

android {
    namespace = "com.song.deviceinfo"
    compileSdk = property("COMPILE_SDK_VERSION").toString().toInt()
    buildToolsVersion = property("BUILD_TOOLS_VERSION").toString()
    
    defaultConfig {
        applicationId = "com.song.deviceinfo"
        minSdk = property("MIN_SDK_VERSION").toString().toInt()
        targetSdk = property("TARGET_SDK_VERSION").toString().toInt()
        versionCode = property("VERSION_CODE").toString().toInt()
        versionName = property("VERSION_NAME").toString()
        multiDexEnabled = true
        
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        
        ndk {
            // 设置支持的SO库架构
            abiFilters += listOf("x86", "armeabi-v7a", "x86_64", "arm64-v8a")
        }
        externalNativeBuild {
            cmake {
                cppFlags += "-std=c++11"
            }
        }
        buildFeatures {
            viewBinding = true
        }
    }
    
    signingConfigs {
        create("release") {
            keyAlias = "geetest"
            keyPassword = "geetest"
            storeFile = file("../public.jks")
            storePassword = "geetest"
        }
    }
    
    buildTypes {
        release {
            isMinifyEnabled = false // 混淆
            isZipAlignEnabled = true // Zipalign优化
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            isMinifyEnabled = false // 混淆
            isZipAlignEnabled = true // Zipalign优化
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        create("customDebugType") {
            isDebuggable = true
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    
    kotlinOptions {
        jvmTarget = "1.8"
    }
    
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.18.1"
        }
    }
    
    buildFeatures {
        viewBinding = true
    }
    
    lint {
        abortOnError = false
    }
    
    applicationVariants.all {
        val variant = this
        outputs.all {
            val output = this as com.android.build.gradle.internal.api.BaseVariantOutputImpl
            output.outputFileName = if (variant.buildType.name == "debug") {
                "${PRODUCT_NAME}${variant.versionName}_${DATE}_debug.apk"
            } else {
                "${PRODUCT_NAME}${variant.versionName}_${DATE}.apk"
            }
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.preference)
    implementation(libs.androidx.multidex)
    
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    
    implementation(libs.bugly.crashreport)
    implementation(libs.bugly.nativecrashreport)
    
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.perf)
    
    implementation(files("libs/miit_mdid_1.0.10.aar"))
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    
    debugImplementation(libs.doraemonkit)
    releaseImplementation(libs.doraemonkit.no.op)
    
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}