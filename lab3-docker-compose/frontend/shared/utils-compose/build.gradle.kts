plugins {
    id("compose-setup")
}
apply("generateResourcesObjects.gradle.kts")

kotlin {
    sourceSets {
        commonMain {
            kotlin.srcDir(project.layout.buildDirectory.dir("generated/sources/resourceObjects"))
            dependencies {
                implementation(project(Modules.utils))

//                implementation(libs.kotlinx.coroutines)
                implementation(libs.bundles.coil)
                implementation(compose.components.resources)

                implementation(libs.bundles.decompose)
            }

        }
    }
}

compose.resources {
    generateResClass = always
    this.publicResClass = true
    packageOfResClass = "careshare.shared"
    this.nameOfResClass = "SharedRes"
}

android {
    namespace = Config.Android.namespace(Modules.utilsCompose)
}