package ch.dreipol.dreimultiplatform

import co.touchlab.kermit.CommonLogger
import co.touchlab.kermit.Kermit

fun kermit(): Kermit {
    return Kermit(CommonLogger())
}