package ru.hitsbank.bank_common.data.repository

import kotlinx.coroutines.Dispatchers
import ru.hitsbank.bank_common.data.api.ProfileApi
import ru.hitsbank.bank_common.data.datasource.SessionManager
import ru.hitsbank.bank_common.data.mapper.ProfileMapper
import ru.hitsbank.bank_common.data.utils.apiCall
import ru.hitsbank.bank_common.data.utils.toResult
import ru.hitsbank.bank_common.domain.map
import ru.hitsbank.bank_common.domain.model.ProfileEntity
import ru.hitsbank.bank_common.domain.repository.IProfileRepository
import ru.hitsbank.bank_common.domain.Result

import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val profileApi: ProfileApi,
    private val sessionManager: SessionManager,
    private val mapper: ProfileMapper,
) : IProfileRepository {

    override suspend fun getSelfProfile(): Result<ProfileEntity> {
        return apiCall(Dispatchers.IO) {
            profileApi.getSelfProfile()
                .toResult()
                .also { result ->
                    if (result is Result.Success) {
                        sessionManager.saveIsUserBlocked(result.data.isBlocked)
                    }
                }
                .map(mapper::map)
        }
    }
}