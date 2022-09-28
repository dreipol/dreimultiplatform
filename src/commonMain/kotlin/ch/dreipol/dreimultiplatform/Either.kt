package ch.dreipol.dreimultiplatform

sealed class Either<A, B> {
    data class First<A, B>(val value: A) : Either<A, B>()
    data class Second<A, B>(val value: B) : Either<A, B>()

    fun getFirst(): A {
        if (this is First) return value
        else throw IllegalStateException()
    }

    fun getSecond(): B {
        if (this is Second) return value
        else throw IllegalStateException()
    }
}
