package ch.dreipol.dreimultiplatform

fun <T> Sequence<T>.onFirst(action: (T) -> Unit): Sequence<T> = onEachIndexed { index, element ->
    if (index == 0) {
        action(element)
    }
}