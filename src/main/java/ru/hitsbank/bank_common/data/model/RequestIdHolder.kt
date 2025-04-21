package ru.hitsbank.bank_common.data.model

import java.util.UUID

data class RequestIdHolder(
    val requestId: String,
    val parametersHashCode: Int,
)

fun RequestIdHolder?.getNewRequestId(hashCode: Int): RequestIdHolder {
    if (this == null) {
        return RequestIdHolder(
            UUID.randomUUID().toString(),
            hashCode,
        )
    }

    return if (parametersHashCode == hashCode) {
        this
    } else {
        RequestIdHolder(
            UUID.randomUUID().toString(),
            hashCode,
        )
    }
}