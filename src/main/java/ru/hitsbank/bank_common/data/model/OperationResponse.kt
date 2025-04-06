package ru.hitsbank.bank_common.data.model

import ru.hitsbank.bank_common.domain.entity.CurrencyCode

data class OperationResponse(
    val id: String,
    val executedAt: String,
    val type: OperationTypeResponse,
    val amount: String,
    val currencyCode: CurrencyCode,
)

enum class OperationTypeResponse {
    WITHDRAW,
    TOP_UP,
    LOAN_PAYMENT,
    TRANSFER_INCOMING,
    TRANSFER_OUTGOING,
}