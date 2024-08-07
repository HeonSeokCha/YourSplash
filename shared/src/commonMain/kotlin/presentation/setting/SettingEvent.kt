package presentation.setting

sealed class SettingEvent {
    data class PutSettingValue(val key: String, val value: String) : SettingEvent()
}