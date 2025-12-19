plugins {
    id("data-ktor-setup")
    id("data-settings-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(Modules.Auth.domain))
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.Auth.data)
}