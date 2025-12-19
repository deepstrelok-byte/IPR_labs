plugins {
    id("presentation-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(Modules.Auth.domain))
            implementation(libs.backhandler)
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.Auth.presentation)
}