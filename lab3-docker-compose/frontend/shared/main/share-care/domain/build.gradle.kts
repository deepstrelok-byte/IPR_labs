plugins {
    id("domain-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(Modules.Main.Common.domain))
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.Main.ShareCare.domain)
}