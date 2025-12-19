import org.gradle.accessors.dm.LibrariesForLibs
val libs = the<LibrariesForLibs>()
plugins {
    id("shared-setup")
}


kotlin {
    sourceSets {
        commonMain.dependencies {
//            implementation(project(Modules.core))

            implementation(libs.bundles.settings)

        }
    }
}