package ru.hitsbank.bank_common.domain.model

import ru.hitsbank.bank_common.domain.entity.RoleType

data class ProfileEntity(
    val id: String,
    val firstName: String,
    val lastName: String,
    val isBlocked: Boolean,
    val roles: List<RoleType>,
)