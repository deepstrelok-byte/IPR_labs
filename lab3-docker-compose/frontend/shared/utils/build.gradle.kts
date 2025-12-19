plugins {
    id("shared-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.decompose.core)
            implementation(libs.kotlinx.coroutines)
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.utils)
}