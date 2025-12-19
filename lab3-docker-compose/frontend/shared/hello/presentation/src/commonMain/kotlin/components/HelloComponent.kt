package components

import kotlinx.coroutines.flow.MutableStateFlow

interface HelloComponent {
    val num: MutableStateFlow<Int>

    val output: (Output) -> Unit

    sealed class Output {
        data object NavigateToAuth : Output()
    }
}