plugins {
    id("presentation-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(Modules.ItemEditor.domain))
        }
        androidMain.dependencies {
            implementation(libs.accompanist.permissions)
            implementation(libs.bundles.androidx.camera)
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.ItemEditor.presentation)
}