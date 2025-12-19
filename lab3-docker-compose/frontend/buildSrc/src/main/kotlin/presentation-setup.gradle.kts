import org.gradle.accessors.dm.LibrariesForLibs
val libs = the<LibrariesForLibs>()
plugins {
    id("compose-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
//            implementation(project(Modules.core))

            implementation(compose.components.resources)

            implementation(libs.decompose.compose)
            implementation(project(Modules.utilsCompose))

            api(libs.koin.core)
            api(libs.decompose.core)

            implementation(libs.kotlinx.coroutines)
            implementation(project(Modules.utils))
        }
    }
}

compose.resources {
    generateResClass = auto
    this.publicResClass = false
}