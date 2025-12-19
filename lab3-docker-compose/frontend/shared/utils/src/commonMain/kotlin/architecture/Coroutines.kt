package architecture

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

val mainContext = Dispatchers.Main
val ioContext = Dispatchers.IO

fun CoroutineContext.named(name: String) = this + CoroutineName(name)

fun CoroutineScope.launchMain(block: suspend CoroutineScope.() -> Unit) =
    launch(context = this.coroutineContext + mainContext) { block() }

fun CoroutineScope.launchIO(block: suspend CoroutineScope.() -> Unit) =
    launch(context = this.coroutineContext + ioContext) { block() }