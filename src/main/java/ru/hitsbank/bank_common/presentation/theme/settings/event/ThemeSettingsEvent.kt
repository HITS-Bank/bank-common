package ru.hitsbank.bank_common.presentation.theme.settings.event

import ru.hitsbank.bank_common.presentation.theme.settings.model.ThemeDropdownItem

sealed interface ThemeSettingsEvent {

    data class ThemeSelected(val themeDropdownItem: ThemeDropdownItem): ThemeSettingsEvent

    data class DropdownExpanded(val isExpanded: Boolean): ThemeSettingsEvent
}