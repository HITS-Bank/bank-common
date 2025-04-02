package ru.hitsbank.bank_common.data.model

data class LoginRequest(
    val email: String,
    val password: String,
)