package ru.hitsbank.bank_common.domain.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.hitsbank.bank_common.domain.Completable
import ru.hitsbank.bank_common.domain.State
import ru.hitsbank.bank_common.domain.repository.IAuthRepository
import ru.hitsbank.bank_common.domain.repository.IProfileRepository
import ru.hitsbank.bank_common.domain.toState
import ru.hitsbank.bank_common.domain.Result
import ru.hitsbank.bank_common.domain.entity.RoleType
import ru.hitsbank.bank_common.domain.repository.IThemeRepository
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val authRepository: IAuthRepository,
    private val profileRepository: IProfileRepository,
    private val personalizationRepository: IThemeRepository,
) {

    fun exchangeAuthCodeForToken(code: String, roleType: RoleType): Flow<State<Completable>> = flow {
        emit(State.Loading)
        emit(authRepository.exchangeAuthCodeForToken(code, roleType).toState())

        when (val userProfile = profileRepository.getSelfProfile()) {
            is Result.Error -> emit(userProfile.toState())
            is Result.Success -> authRepository.saveIsUserBlocked(userProfile.data.isBlocked)
        }

        personalizationRepository.updateThemeFromRemote(roleType)
    }

    fun getIsUserBlocked(): Flow<State<Boolean>> = flow {
        emit(State.Loading)
        emit(authRepository.getIsUserBlocked().toState())
    }
}
