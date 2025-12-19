plugins {
    id("presentation-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(Modules.Main.Common.presentation))
            implementation(project(Modules.Main.Common.domain))
            implementation(project(Modules.Main.FindHelp.domain))

            implementation(project(Modules.Auth.domain)) // for userId
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.Main.FindHelp.presentation)
}