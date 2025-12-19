plugins {
    id("data-ktor-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(Modules.Profile.domain))
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.Profile.data)
}