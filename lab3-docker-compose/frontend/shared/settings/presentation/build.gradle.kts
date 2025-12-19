plugins {
    id("presentation-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(Modules.Settings.domain))
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.Settings.presentation)
}