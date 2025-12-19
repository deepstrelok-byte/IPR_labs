package components

import kotlinx.coroutines.flow.MutableStateFlow

object FakeHelloComponent: HelloComponent {
    override val num: MutableStateFlow<Int> = MutableStateFlow(1)
    override val output: (HelloComponent.Output) -> Unit
        get() = {}
}