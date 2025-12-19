plugins {
    id("data-ktor-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(Modules.Main.FindHelp.domain))

            implementation(project(Modules.Main.Common.data))
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.Main.FindHelp.data)
}