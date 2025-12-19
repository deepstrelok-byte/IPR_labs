import SwiftUI


@main
struct iOSApp: App {
    init() {

    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}


private func loadRocketSimConnect() {
    #if DEBUG
    guard (Bundle(path: "/Applications/RocketSim.app/Contents/Frameworks/RocketSimConnectLinker.nocache.framework")?.load() == true) else {
        print("Failed to load linker framework")
        return
    }
    print("RocketSim Connect successfully linked")
    #endif
}