package ch.dreipol.dreimultiplatform

import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
fun registerForKotlinExceptions(block: (Throwable, String) -> Unit) {
    setUnhandledExceptionHook {
        block(it, it.stackTraceToString())
    }
}