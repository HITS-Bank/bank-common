package ru.hitsbank.bank_common.domain.repository

import ru.hitsbank.bank_common.domain.model.ProfileEntity
import ru.hitsbank.bank_common.domain.Result

interface IProfileRepository {

    suspend fun getSelfProfile(): Result<ProfileEntity>
}