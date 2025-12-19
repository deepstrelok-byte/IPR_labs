package components

import architecture.mainContext
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreateSimple
import com.arkivanov.essenty.lifecycle.doOnResume
import com.arkivanov.essenty.lifecycle.doOnStop
import components.HelloComponent.Output
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RealHelloComponent(
    componentContext: ComponentContext,
    override val output: (Output) -> Unit
) : HelloComponent, ComponentContext by componentContext {

    // some tests
    override val num = instanceKeeper.getOrCreateSimple { MutableStateFlow(0) }

    private val coroutineScope = CoroutineScope(mainContext + SupervisorJob())

    private var job: Job? = null

    fun start() {
        job = coroutineScope.launch {
            while (true) {
                delay(1000)
                num.value++
            }
        }
    }


    init {
        lifecycle.doOnStop {
            job?.cancel()
        }


        lifecycle.doOnResume {
            start()
        }

    }
}