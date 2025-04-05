package ru.hitsbank.bank_common.domain.repository

import ru.hitsbank.bank_common.domain.Result
import ru.hitsbank.bank_common.domain.Completable
import ru.hitsbank.bank_common.domain.entity.RoleType

interface IAuthRepository {

    suspend fun exchangeAuthCodeForToken(code: String, roleType: RoleType): Result<Completable>

    suspend fun refresh(): Result<Completable>

    fun saveIsUserBlocked(isUserBlocked: Boolean)

    fun getIsUserBlocked(): Result<Boolean>
}