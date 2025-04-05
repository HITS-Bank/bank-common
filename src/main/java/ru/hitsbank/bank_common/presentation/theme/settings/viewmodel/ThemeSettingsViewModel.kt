package ru.hitsbank.bank_common.presentation.theme.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.hitsbank.bank_common.domain.State
import ru.hitsbank.bank_common.domain.entity.RoleType
import ru.hitsbank.bank_common.domain.interactor.ThemeInteractor
import ru.hitsbank.bank_common.presentation.theme.settings.event.ThemeSettingsEffect
import ru.hitsbank.bank_common.presentation.theme.settings.event.ThemeSettingsEvent
import ru.hitsbank.bank_common.presentation.theme.settings.model.ThemeDropdownItem
import ru.hitsbank.bank_common.presentation.theme.settings.model.ThemeSettingsState

@HiltViewModel(assistedFactory = ThemeSettingsViewModel.Factory::class)
class ThemeSettingsViewModel @AssistedInject constructor(
    @Assisted private val roleType: RoleType,
    private val themeInteractor: ThemeInteractor,
) : ViewModel() {

    private val _state = MutableStateFlow(
        ThemeSettingsState.default(
            theme = runBlocking {
                themeInteractor.getTheme().first()
            }
        )
    )
    val state = _state.asStateFlow()

    private val _effects = MutableSharedFlow<ThemeSettingsEffect>()
    val effects = _effects.asSharedFlow()

    init {
        viewModelScope.launch {
            themeInteractor.getTheme().collectLatest { theme ->
                _state.value = _state.value.copy(
                    selectedTheme = ThemeDropdownItem(theme)
                )
            }
        }
    }

    fun onEvent(event: ThemeSettingsEvent) {
        when (event) {
            is ThemeSettingsEvent.ThemeSelected -> {
                if (_state.value.isPerformingAction) return

                viewModelScope.launch {
                    themeInteractor.setTheme(roleType, event.themeDropdownItem.entity)
                        .collectLatest { state ->
                            when (state) {
                                State.Loading -> {
                                    _state.value = _state.value.copy(
                                        isPerformingAction = true,
                                        isDropdownExpanded = false,
                                    )
                                }
                                is State.Error -> {
                                    _state.value = _state.value.copy(
                                        isPerformingAction = false,
                                        isDropdownExpanded = false,
                                    )
                                    _effects.emit(ThemeSettingsEffect.ThemeChangeFailed)
                                }
                                is State.Success -> {
                                    _state.value = _state.value.copy(
                                        isPerformingAction = false,
                                        isDropdownExpanded = false,
                                    )
                                }
                            }
                        }
                }
            }
            is ThemeSettingsEvent.DropdownExpanded -> {
                if (_state.value.isPerformingAction) return

                _state.value = _state.value.copy(
                    isDropdownExpanded = event.isExpanded
                )
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(roleType: RoleType): ThemeSettingsViewModel
    }
}