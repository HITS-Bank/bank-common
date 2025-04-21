package ru.hitsbank.bank_common.data.model

import ru.hitsbank.bank_common.domain.entity.ThemeEntity

data class SetThemeModel(
    val requestId: String,
    val theme: ThemeEntity,
)
