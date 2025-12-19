plugins {
    id("presentation-setup")
    id(libs.plugins.serialization.get().pluginId)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.core)

            implementation(project(Modules.Hello.presentation))
            implementation(project(Modules.Auth.presentation))
            implementation(project(Modules.Auth.domain))

            implementation(project(Modules.Main.Flow.presentation))
            implementation(project(Modules.Main.FindHelp.presentation))
            implementation(project(Modules.Main.ShareCare.presentation))
            implementation(project(Modules.Main.Common.presentation))
            implementation(project(Modules.Main.ItemDetails.presentation))
            implementation(project(Modules.Main.Common.domain))

            implementation(project(Modules.ItemEditor.presentation))
            implementation(project(Modules.Profile.presentation))

            implementation(project(Modules.Settings.domain))
            implementation(project(Modules.Settings.presentation))
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.core)
}