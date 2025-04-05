package ru.hitsbank.bank_common.data.model

import ru.hitsbank.bank_common.domain.entity.RoleType

data class ProfileResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    val isBlocked: Boolean,
    val roles: List<RoleType>,
)
