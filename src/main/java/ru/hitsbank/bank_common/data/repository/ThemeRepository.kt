package ru.hitsbank.bank_common.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import ru.hitsbank.bank_common.data.api.ThemeApi
import ru.hitsbank.bank_common.data.datasource.ThemeManager
import ru.hitsbank.bank_common.data.model.RequestIdHolder
import ru.hitsbank.bank_common.data.model.SetThemeModel
import ru.hitsbank.bank_common.data.model.getNewRequestId
import ru.hitsbank.bank_common.data.utils.apiCall
import ru.hitsbank.bank_common.data.utils.toResult
import ru.hitsbank.bank_common.domain.Completable
import ru.hitsbank.bank_common.domain.Result
import ru.hitsbank.bank_common.domain.entity.RoleType
import ru.hitsbank.bank_common.domain.entity.ThemeEntity
import ru.hitsbank.bank_common.domain.repository.IThemeRepository
import ru.hitsbank.bank_common.domain.toCompletableResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeRepository @Inject constructor(
    private val themeApi: ThemeApi,
    private val themeManager: ThemeManager,
) : IThemeRepository {

    private var requestIdHolder: RequestIdHolder? = null

    override suspend fun updateThemeFromRemote(roleType: RoleType): Result<Completable> {
        return apiCall(Dispatchers.IO) {
            themeApi.getTheme(roleType.name)
                .toResult()
                .also { result ->
                    when (result) {
                        is Result.Error -> Unit
                        is Result.Success -> themeManager.setTheme(result.data.theme)
                    }
                }
                .toCompletableResult()
        }
    }

    override fun getTheme(): Flow<ThemeEntity> {
        return themeManager.getThemeFlow()
    }

    override suspend fun setTheme(roleType: RoleType, theme: ThemeEntity): Result<Completable> {
        return apiCall(Dispatchers.IO) {
            val idHolder = requestIdHolder.getNewRequestId(roleType.hashCode())
            requestIdHolder = idHolder

            themeApi.setTheme(
                roleType.name,
                SetThemeModel(
                    theme,
                ),
                idHolder.requestId,
            )
                .toResult()
                .also { result ->
                    when (result) {
                        is Result.Error -> Unit
                        is Result.Success -> {
                            requestIdHolder = null
                            themeManager.setTheme(result.data.theme)
                        }
                    }
                }
                .toCompletableResult()
        }
    }
}