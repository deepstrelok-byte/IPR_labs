@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

// Couldn't use `libs` here =/
plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization")
}

kotlin {
    androidTarget()
//    iosArm64()
    iosSimulatorArm64()

    applyDefaultHierarchyTemplate {
        common {
            group("skia") {
                withIosSimulatorArm64()
//                withIosArm64()
            }
        }
    }

    jvmToolchain(Config.Java.intVersion)
}

android {
    compileSdk = Config.Android.compileSdk
    defaultConfig {
        minSdk = Config.Android.minSdk
    }
}