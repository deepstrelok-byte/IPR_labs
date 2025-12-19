

plugins {
    id("presentation-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(Modules.Main.Common.domain))
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.Main.Common.presentation)
}