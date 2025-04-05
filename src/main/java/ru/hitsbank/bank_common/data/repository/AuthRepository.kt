package ru.hitsbank.bank_common.data.repository

import kotlinx.coroutines.Dispatchers
import ru.hitsbank.bank_common.Constants.AUTH_CLIENT_ID
import ru.hitsbank.bank_common.Constants.AUTH_REDIRECT_URI
import ru.hitsbank.bank_common.Constants.AUTH_REDIRECT_URI_EMPLOYEE
import ru.hitsbank.bank_common.data.api.AuthApi
import ru.hitsbank.bank_common.data.datasource.SessionManager
import ru.hitsbank.bank_common.data.model.TokenType
import ru.hitsbank.bank_common.data.utils.apiCall
import ru.hitsbank.bank_common.data.utils.toResult
import ru.hitsbank.bank_common.domain.Completable
import ru.hitsbank.bank_common.domain.repository.IAuthRepository
import ru.hitsbank.bank_common.domain.Result
import ru.hitsbank.bank_common.domain.entity.RoleType
import ru.hitsbank.bank_common.domain.toCompletableResult
import javax.inject.Inject

private const val AUTH_CODE_GRANT_TYPE = "authorization_code"
private const val REFRESH_TOKEN_GRANT_TYPE = "refresh_token"

class AuthRepository @Inject constructor(
    private val authApi: AuthApi,
    private val sessionManager: SessionManager,
) : IAuthRepository {

    override suspend fun exchangeAuthCodeForToken(code: String, roleType: RoleType): Result<Completable> {
        return apiCall(Dispatchers.IO) {
            authApi.exchangeAuthCodeForToken(
                clientId = AUTH_CLIENT_ID,
                grantType = AUTH_CODE_GRANT_TYPE,
                code = code,
                redirectUri = when (roleType) {
                    RoleType.EMPLOYEE -> AUTH_REDIRECT_URI_EMPLOYEE
                    RoleType.CLIENT -> AUTH_REDIRECT_URI
                },
            )
                .toResult()
                .also { result ->
                    if (result is Result.Success) {
                        sessionManager.saveTokens(result.data)
                    }
                }
                .toCompletableResult()
        }
    }

    override suspend fun refresh(): Result<Completable> {
        val refreshToken = sessionManager.fetchToken(TokenType.REFRESH)
            ?: return Result.Error(Exception("could not retrieve refresh token"))

        return apiCall(Dispatchers.IO) {
            authApi.refreshToken(
                clientId = AUTH_CLIENT_ID,
                grantType = REFRESH_TOKEN_GRANT_TYPE,
                refreshToken = refreshToken,
            )
                .toResult()
                .also { result ->
                    if (result is Result.Success) {
                        sessionManager.saveTokens(result.data)
                    }
                }
                .toCompletableResult()
        }
    }

    override fun saveIsUserBlocked(isUserBlocked: Boolean) {
        sessionManager.saveIsUserBlocked(isUserBlocked)
    }

    override fun getIsUserBlocked(): Result<Boolean> {
        return Result.Success(sessionManager.isUserBlocked())
    }
}
