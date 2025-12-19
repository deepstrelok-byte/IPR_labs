@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    id(libs.plugins.android.application.get().pluginId)
    id(libs.plugins.kotlin.get().pluginId)
    id(libs.plugins.compose.plugin.get().pluginId)
    id(libs.plugins.compose.multiplatform.get().pluginId)
    id(libs.plugins.serialization.get().pluginId)
}

version = Config.Application.versionName

kotlin {
    androidTarget()

    listOf(
//        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(Modules.root))
            implementation(project(Modules.di))
            implementation(project(Modules.utils))
            implementation(project(Modules.utilsCompose))

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.material3)

            implementation(libs.decompose.core)
            implementation(libs.decompose.compose)
        }
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
        }
    }

    jvmToolchain(Config.Java.intVersion)
}

android {
    val config = Config.Android
    namespace = config.namespace
    compileSdk = config.compileSdk

    defaultConfig {
        applicationId = "org.top_it.careshare"
        minSdk = config.minSdk
        targetSdk = config.targetSdk
        versionCode = Config.Application.versionCode
        versionName = version.toString()
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        release {
            this.matchingFallbacks.add("release")
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("debug")
            // TODO proguard
        }

        debug {
            this.matchingFallbacks.add("debug")
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = Config.Java.javaVersion
        targetCompatibility = Config.Java.javaVersion
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

