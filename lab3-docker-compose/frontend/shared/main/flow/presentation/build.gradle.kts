plugins {
    id("presentation-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(Modules.Main.Common.presentation))
            implementation(project(Modules.Main.Common.domain))

            implementation(project(Modules.Main.ShareCare.presentation))
            implementation(project(Modules.Main.FindHelp.presentation))
            implementation(project(Modules.Main.ItemDetails.presentation))
            implementation(project(Modules.Main.RequestDetails.presentation))

            implementation(project(Modules.Auth.domain))
            implementation(project(Modules.Settings.domain))

            implementation(libs.kotlinx.datetime)
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.Main.Flow.presentation)
}