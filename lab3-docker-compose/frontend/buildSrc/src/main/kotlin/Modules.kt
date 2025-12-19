@file:Suppress("ConstPropertyName")
object Modules {
    const val composeApp = "composeApp"

    const val sharedPath = ":shared"

    const val root = "$sharedPath:root"
    const val core = "$sharedPath:core"
    const val di = "$sharedPath:di"
    const val utils = "$sharedPath:utils"
    const val utilsCompose = "$sharedPath:utils-compose"

    object Hello {
        private const val modulePath = "$sharedPath:hello"

        const val data = "$modulePath:data"
        const val domain = "$modulePath:domain"
        const val presentation = "$modulePath:presentation"
    }

    object Auth {
        private const val modulePath = "$sharedPath:auth"

        const val data = "$modulePath:data"
        const val domain = "$modulePath:domain"
        const val presentation = "$modulePath:presentation"
    }
    object Main {
        private const val modulePath = "$sharedPath:main"

        object Common {
            private const val modulePath = "${Main.modulePath}:common"
            const val presentation = "$modulePath:presentation"
            const val domain = "$modulePath:domain"
            const val data = "$modulePath:data"
        }
        object Flow {
            private const val modulePath = "${Main.modulePath}:flow"
            const val presentation = "$modulePath:presentation"
        }

        object ShareCare {
            private const val modulePath = "${Main.modulePath}:share-care"
            const val data = "$modulePath:data"
            const val domain = "$modulePath:domain"
            const val presentation = "$modulePath:presentation"
        }
        object FindHelp {
            private const val modulePath = "${Main.modulePath}:find-help"
            const val data = "$modulePath:data"
            const val domain = "$modulePath:domain"
            const val presentation = "$modulePath:presentation"
        }
        object ItemDetails {
            private const val modulePath = "${Main.modulePath}:item-details"
            const val data = "$modulePath:data"
            const val domain = "$modulePath:domain"
            const val presentation = "$modulePath:presentation"
        }
        object RequestDetails {
            private const val modulePath = "${Main.modulePath}:request-details"
            const val data = "$modulePath:data"
            const val domain = "$modulePath:domain"
            const val presentation = "$modulePath:presentation"
        }
    }


    object ItemEditor {
        private const val modulePath = "$sharedPath:item-editor"
        const val data = "$modulePath:data"
        const val domain = "$modulePath:domain"
        const val presentation = "$modulePath:presentation"
    }

    object Profile {
        private const val modulePath = "$sharedPath:profile"

        const val data = "$modulePath:data"
        const val domain = "$modulePath:domain"
        const val presentation = "$modulePath:presentation"
    }

    object Settings {
        private const val modulePath = "$sharedPath:settings"

        const val data = "$modulePath:data"
        const val domain = "$modulePath:domain"
        const val presentation = "$modulePath:presentation"
    }
}