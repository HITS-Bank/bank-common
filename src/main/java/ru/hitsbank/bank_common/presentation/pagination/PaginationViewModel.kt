package ru.hitsbank.bank_common.presentation.pagination

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.hitsbank.bank_common.domain.State
import ru.hitsbank.bank_common.domain.map
import ru.hitsbank.bank_common.presentation.common.BankUiState
import ru.hitsbank.bank_common.presentation.common.getIfSuccess
import ru.hitsbank.bank_common.presentation.common.updateIfSuccess

abstract class PaginationViewModel<T, R: PaginationStateHolder<T>>(initState: BankUiState<R>) : PaginationViewModelBase<T, R>(initState) {

    final override fun getNextPage(pageNumber: Int): Flow<State<PageInfo<T>>> {
        val pageSize = _state.value.getIfSuccess()?.pageSize ?: return flowOf(State.Error())
        return getNextPageContents(pageNumber).map { state ->
            state.map { list ->
                PageInfo(
                    content = list,
                    paginationFinished = list.size < pageSize
                )
            }
        }
    }

    protected abstract fun getNextPageContents(pageNumber: Int): Flow<State<List<T>>>
}

@Suppress("UNCHECKED_CAST")
abstract class PaginationViewModelBase<T, R: PaginationStateHolder<T>>(initState: BankUiState<R>) : ViewModel() {

    protected val _state = MutableStateFlow(initState)
    val state = _state.asStateFlow()

    private val paginationEvents = MutableSharedFlow<PaginationEvent>()

    init {
        subscribeToPaginationEvents()
    }

    fun onPaginationEvent(event: PaginationEvent) {
        viewModelScope.launch {
            paginationEvents.emit(event)
        }
    }

    private fun subscribeToPaginationEvents() {
        viewModelScope.launch {
            paginationEvents.collectLatest { event ->
                processPaginationEvent(event)
            }
        }
    }

    private suspend fun processPaginationEvent(event: PaginationEvent) {
        when (event) {
            is PaginationEvent.LoadNextPage -> {
                _state.updateIfSuccess(onUpdated = { loadPage() }) { state ->
                    state.copyWith(paginationState = PaginationState.Loading) as R
                }
            }

            is PaginationEvent.Reload -> {
                _state.updateIfSuccess(onUpdated = { loadPage() }) { state ->
                    state.resetPagination().copyWith(paginationState = PaginationState.Loading) as R
                }
            }
        }
    }

    private suspend fun loadPage() {
        val stateValue = state.getIfSuccess() ?: return
        getNextPage(stateValue.pageNumber).collect { state ->
            when (state) {
                is State.Error -> _state.updateIfSuccess { oldState ->
                    oldState.copyWith(paginationState = PaginationState.Error) as R
                }
                State.Loading -> Unit
                is State.Success -> _state.updateIfSuccess { oldState ->
                    oldState.copyWith(
                        paginationState =
                            if (state.data.paginationFinished) PaginationState.EndReached
                            else PaginationState.Idle,
                        data = oldState.data + state.data.content,
                        pageNumber = oldState.pageNumber + 1,
                    ) as R
                }
            }
        }
    }

    protected abstract fun getNextPage(pageNumber: Int): Flow<State<PageInfo<T>>>
}

data class PageInfo<T>(
    val content: List<T>,
    val paginationFinished: Boolean,
)