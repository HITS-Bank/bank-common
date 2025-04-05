package ru.hitsbank.bank_common.presentation.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.hitsbank.bank_common.domain.entity.RoleType
import ru.hitsbank.bank_common.domain.interactor.ThemeInteractor

@HiltViewModel(assistedFactory = ThemeViewModel.Factory::class)
class ThemeViewModel @AssistedInject constructor(
    @Assisted roleType: RoleType,
    private val themeInteractor: ThemeInteractor,
) : ViewModel() {

    val themeFlow = MutableStateFlow(
        value = runBlocking {
            themeInteractor.getTheme().first()
        }
    )

    init {
        viewModelScope.launch {
            themeInteractor.getTheme().collect { theme ->
                themeFlow.value = theme
            }
        }
        viewModelScope.launch {
            themeInteractor.updateThemeFromRemote(roleType)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(roleType: RoleType): ThemeViewModel
    }
}