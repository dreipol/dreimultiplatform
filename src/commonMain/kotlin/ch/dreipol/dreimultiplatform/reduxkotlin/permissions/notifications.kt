package ch.dreipol.dreimultiplatform.reduxkotlin.permissions

enum class NotificationPermission(val value: Int) {
    NOT_DETERMINED(0),
    DENIED(1),
    AUTHORIZED(2),
    PROVISIONAL(3),
    EPHEMERAL(4);

    companion object {
        fun fromInt(value: Int) = values().first { it.value == value }
    }
}
