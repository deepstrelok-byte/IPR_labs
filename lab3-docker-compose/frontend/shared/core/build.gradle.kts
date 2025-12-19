plugins {
    id("shared-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.bundles.ktor)
            implementation(libs.bundles.settings)

            implementation(project(Modules.utils))
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.core)
}