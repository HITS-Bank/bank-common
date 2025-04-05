package ru.hitsbank.bank_common.data.mapper

import ru.hitsbank.bank_common.data.model.ProfileResponse
import ru.hitsbank.bank_common.domain.model.ProfileEntity
import javax.inject.Inject

class ProfileMapper @Inject constructor() {

    fun map(response: ProfileResponse): ProfileEntity {
        with (response) {
            return ProfileEntity(
                id = id,
                firstName = firstName,
                lastName = lastName,
                isBlocked = isBlocked,
                roles = roles,
            )
        }
    }
}