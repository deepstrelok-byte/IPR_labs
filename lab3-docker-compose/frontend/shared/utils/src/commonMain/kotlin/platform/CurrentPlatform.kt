package platform

enum class Platform {
    IOS, Android
}

expect val currentPlatform: Platform