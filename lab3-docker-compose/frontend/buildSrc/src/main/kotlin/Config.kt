@file:Suppress(
    "SpellCheckingInspection",
    "MemberVisibilityCanBePrivate",
    "ConstPropertyName"
)

import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget


object Config {
    object Application {
        const val versionCode = 1
        const val versionName = "1.0.0"
    }

    object Android {
        const val namespace = "org.top_it.careshare"
        const val compileSdk = 35
        const val minSdk = 33
        const val targetSdk = 35

        fun namespace(module: String): String {
            val conventionPluginName = "$namespace.convention"
            val formattedModuleName = module
                .replace(Modules.sharedPath, "")
                .replace(Regex("[:-]"), ".")
            return "$conventionPluginName$formattedModuleName"
        }
    }

    object Java {

        val javaVersion = JavaVersion.VERSION_21
        val version = JvmTarget.JVM_21
        val stringVersion = version.target
        val intVersion = stringVersion.toInt()
    }
}