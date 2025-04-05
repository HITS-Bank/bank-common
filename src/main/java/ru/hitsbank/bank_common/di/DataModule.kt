package ru.hitsbank.bank_common.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.hitsbank.bank_common.data.repository.AuthRepository
import ru.hitsbank.bank_common.data.repository.ProfileRepository
import ru.hitsbank.bank_common.data.repository.ThemeRepository
import ru.hitsbank.bank_common.domain.repository.IAuthRepository
import ru.hitsbank.bank_common.domain.repository.IProfileRepository
import ru.hitsbank.bank_common.domain.repository.IThemeRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindAuthRepository(
        authRepository: AuthRepository
    ): IAuthRepository

    @Binds
    abstract fun bindProfileRepository(
        profileRepository: ProfileRepository
    ): IProfileRepository

    @Binds
    abstract fun bindThemeRepository(
        themeRepository: ThemeRepository
    ): IThemeRepository
}