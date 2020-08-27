package ch.dreipol.dreimultiplatform

import co.touchlab.kermit.CommonLogger
import co.touchlab.kermit.Kermit
import io.ktor.client.features.logging.*

fun kermit(): Kermit {
    return Kermit(CommonLogger())
}

class HttpLogger : Logger {
    override fun log(message: String) {
        kermit().d { message }
    }
}