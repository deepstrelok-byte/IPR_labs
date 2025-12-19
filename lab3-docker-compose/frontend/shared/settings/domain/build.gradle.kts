plugins {
    id("domain-setup")
}


android {
    namespace = Config.Android.namespace(Modules.Settings.domain)
}