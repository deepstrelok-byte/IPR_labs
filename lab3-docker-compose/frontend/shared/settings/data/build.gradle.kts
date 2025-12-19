plugins {
    id("data-settings-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(Modules.Settings.domain))
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.Settings.data)
}