plugins {
    id("data-ktor-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(Modules.Main.RequestDetails.domain))
            implementation(project(Modules.Main.Common.data))
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.Main.RequestDetails.data)
}
