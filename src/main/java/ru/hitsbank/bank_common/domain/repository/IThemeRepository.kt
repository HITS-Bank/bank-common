package ru.hitsbank.bank_common.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.hitsbank.bank_common.domain.Completable
import ru.hitsbank.bank_common.domain.Result
import ru.hitsbank.bank_common.domain.entity.RoleType
import ru.hitsbank.bank_common.domain.entity.ThemeEntity

interface IThemeRepository {

    suspend fun updateThemeFromRemote(roleType: RoleType): Result<Completable>

    fun getTheme(): Flow<ThemeEntity>

    suspend fun setTheme(roleType: RoleType, theme: ThemeEntity): Result<Completable>
}