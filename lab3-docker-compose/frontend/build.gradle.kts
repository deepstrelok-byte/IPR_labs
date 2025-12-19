

plugins {
    //trick: for the same plugin versions in all sub-modules
    id(libs.plugins.android.application.get().pluginId) apply false
    id(libs.plugins.kotlin.get().pluginId) apply false
    id(libs.plugins.compose.multiplatform.get().pluginId) apply false
    id(libs.plugins.compose.plugin.get().pluginId) apply false
    id(libs.plugins.ksp.get().pluginId) apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()

        maven("https://packages.jetbrains.team/maven/p/kpm/public/")
    }

}