package ru.hitsbank.bank_common.domain.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.hitsbank.bank_common.domain.Completable
import ru.hitsbank.bank_common.domain.State
import ru.hitsbank.bank_common.domain.entity.RoleType
import ru.hitsbank.bank_common.domain.entity.ThemeEntity
import ru.hitsbank.bank_common.domain.repository.IAuthRepository
import ru.hitsbank.bank_common.domain.repository.IThemeRepository
import ru.hitsbank.bank_common.domain.toState
import javax.inject.Inject

class ThemeInteractor @Inject constructor(
    private val personalizationRepository: IThemeRepository,
    private val authRepository: IAuthRepository,
) {

    fun updateThemeFromRemote(roleType: RoleType): Flow<State<Completable>> = flow {
        if (authRepository.isUserLoggedIn()) {
            emit(State.Loading)
            emit(personalizationRepository.updateThemeFromRemote(roleType).toState())
        }
    }

    fun getTheme() = personalizationRepository.getTheme()

    fun setTheme(roleType: RoleType, theme: ThemeEntity): Flow<State<Completable>> = flow {
        emit(State.Loading)
        emit(personalizationRepository.setTheme(roleType, theme).toState())
    }
}