import org.gradle.accessors.dm.LibrariesForLibs
val libs = the<LibrariesForLibs>()
plugins {
    id("shared-setup")
}


kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(Modules.core))
            implementation(project(Modules.utils))

            implementation(libs.ktor.client.core)
            implementation(libs.bundles.serialization)
        }
    }
}