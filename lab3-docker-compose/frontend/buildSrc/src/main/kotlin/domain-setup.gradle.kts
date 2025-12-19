import org.gradle.accessors.dm.LibrariesForLibs
val libs = the<LibrariesForLibs>()
plugins {
    id("shared-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(Modules.utils))

            // for flow
            implementation(libs.kotlinx.coroutines)
        }
    }
}