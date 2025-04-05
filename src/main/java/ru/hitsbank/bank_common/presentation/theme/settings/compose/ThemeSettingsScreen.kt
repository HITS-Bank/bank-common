package ru.hitsbank.bank_common.presentation.theme.settings.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.hitsbank.bank_common.presentation.common.component.LoadingContentOverlay
import ru.hitsbank.bank_common.presentation.common.component.dropdown.DropdownField
import ru.hitsbank.bank_common.presentation.common.rememberCallback
import ru.hitsbank.bank_common.presentation.theme.S22_W400
import ru.hitsbank.bank_common.presentation.theme.settings.model.ThemeSettingsState
import ru.hitsbank.bank_common.presentation.theme.settings.viewmodel.ThemeSettingsViewModel
import ru.hitsbank.bank_common.presentation.theme.settings.event.ThemeSettingsEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSettingsScreen(viewModel: ThemeSettingsViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = rememberCallback(viewModel::onEvent)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                windowInsets = WindowInsets(0),
                title = {
                    Text(
                        text = "Персонализация",
                        style = S22_W400,
                    )
                },
            )
        }
    ) { paddings ->
        Column(modifier = Modifier.padding(paddings).padding(16.dp)) {
            DropdownField(
                modifier = Modifier.fillMaxWidth(),
                items = ThemeSettingsState.dropdownItems,
                selectedItem = state.selectedTheme,
                onItemSelected = { onEvent(ThemeSettingsEvent.ThemeSelected(it)) },
                isDropdownOpen = state.isDropdownExpanded,
                onOpenDropdown = { onEvent(ThemeSettingsEvent.DropdownExpanded(true)) },
                onCloseDropdown = { onEvent(ThemeSettingsEvent.DropdownExpanded(false)) },
                label = "Тема",
            )
        }
    }

    if (state.isPerformingAction) {
        LoadingContentOverlay()
    }
}