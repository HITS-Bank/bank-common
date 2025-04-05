package ru.hitsbank.bank_common.presentation.theme.settings.event

sealed interface ThemeSettingsEffect {

    data object ThemeChangeFailed : ThemeSettingsEffect
}