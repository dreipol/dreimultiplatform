package ch.zkb.cassie.shared.ch.zkb.cassie.plattformDependent

import kotlin.coroutines.CoroutineContext

expect val defaultDispatcher: CoroutineContext

expect val uiDispatcher: CoroutineContext