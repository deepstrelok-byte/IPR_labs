plugins {
    id("data-ktor-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(Modules.ItemEditor.domain))
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.ItemEditor.data)
}
