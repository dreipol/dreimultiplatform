package ch.dreipol.dreimultiplatform

import platform.Foundation.NSUUID

actual object UUID {
    actual fun generateUUID(): String = NSUUID.UUID().UUIDString
}