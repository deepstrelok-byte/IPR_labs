plugins {
    id("presentation-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {

        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.Hello.presentation)
}