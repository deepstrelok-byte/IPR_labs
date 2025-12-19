plugins {
    id("shared-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.koin.core)

            implementation(project(Modules.core))

            implementation(project(Modules.Auth.data))

            implementation(project(Modules.ItemEditor.data))
            implementation(project(Modules.Main.RequestDetails.data))
            implementation(project(Modules.Main.ItemDetails.data))
            implementation(project(Modules.Main.ItemDetails.presentation))

            
            implementation(project(Modules.Main.ShareCare.data))
            implementation(project(Modules.Main.FindHelp.data))

            implementation(project(Modules.Settings.data))

            implementation(project(Modules.Profile.data))





            implementation(libs.ktor.client.core) // Cannot access class 'HttpClient'
            implementation(libs.settings.core) // Cannot access class 'Settings'
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.di)
}