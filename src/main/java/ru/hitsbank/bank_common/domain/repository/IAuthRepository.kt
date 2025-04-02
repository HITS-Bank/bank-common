package ru.hitsbank.bank_common.domain.repository

import ru.hitsbank.bank_common.domain.Result
import ru.hitsbank.bank_common.domain.Completable

interface IAuthRepository {

    suspend fun exchangeAuthCodeForToken(code: String): Result<Completable>

    suspend fun refresh(): Result<Completable>

    fun saveIsUserBlocked(isUserBlocked: Boolean)

    fun getIsUserBlocked(): Result<Boolean>
}