import org.gradle.accessors.dm.LibrariesForLibs
val libs = the<LibrariesForLibs>()
plugins {
    id("shared-setup")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}


kotlin {
    sourceSets {
        commonMain.dependencies {
            runtimeOnly(compose.runtime)
            implementation(compose.ui)

            implementation(compose.foundation)
            implementation(compose.materialIconsExtended)
            implementation(libs.compose.material.expressive)

            implementation(compose.components.uiToolingPreview)

            implementation(libs.bundles.haze)
        }

        androidMain.dependencies {
            implementation(compose.uiTooling)
        }
    }
    compilerOptions {
        optIn.addAll("androidx.compose.material3.ExperimentalMaterial3Api",
            "androidx.compose.material3.ExperimentalMaterial3ExpressiveApi")
    }
}