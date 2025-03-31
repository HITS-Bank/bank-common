package ru.hitsbank.bank_common.presentation.pagination

sealed interface PaginationEvent {

    object LoadNextPage : PaginationEvent

    object Reload : PaginationEvent
}