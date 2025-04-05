package ru.hitsbank.bank_common.presentation.theme.settings.model

import ru.hitsbank.bank_common.domain.entity.ThemeEntity
import ru.hitsbank.bank_common.presentation.common.component.dropdown.DropdownItem

data class ThemeSettingsState(
    val isPerformingAction: Boolean,
    val selectedTheme: ThemeDropdownItem,
    val isDropdownExpanded: Boolean,
) {

    companion object {
        val dropdownItems = ThemeEntity.entries.map {
            ThemeDropdownItem(it)
        }

        fun default(theme: ThemeEntity) = ThemeSettingsState(
            isPerformingAction = false,
            selectedTheme = ThemeDropdownItem(theme),
            isDropdownExpanded = false,
        )
    }
}

data class ThemeDropdownItem(
    val entity: ThemeEntity
) : DropdownItem {
    override val title = when (entity) {
        ThemeEntity.DARK -> "Тёмная"
        ThemeEntity.LIGHT -> "Светлая"
        ThemeEntity.SAME_AS_SYSTEM -> "Системная"
    }
}