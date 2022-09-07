package ch.dreipol.dreimultiplatform

expect fun launchAndWait(block: suspend () -> Unit)

expect fun <T : Any> T.freezeInstance(): T