plugins {
    id("presentation-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(Modules.Main.Common.presentation))
            implementation(project(Modules.Main.Common.domain))
            implementation(project(Modules.Main.ShareCare.domain))
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.Main.ShareCare.presentation)
}