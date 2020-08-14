package ch.dreipol.dreimultiplatform

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

actual fun launchAndWait(block: suspend () -> Unit) {
    runBlocking { GlobalScope.launch { block.invoke() }.join() }
}