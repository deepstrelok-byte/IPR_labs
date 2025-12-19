plugins {
    id("data-ktor-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(Modules.Main.Common.domain))
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.Main.Common.data)
}