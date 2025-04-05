package ru.hitsbank.bank_common

object Constants {

    const val CLIENT_APP_CHANNEL = "CLIENT"

    const val DEEPLINK_SCHEME_SEPARATOR = "://"
    const val DEEPLINK_PART_SEPARATOR = "/"

    const val DEEPLINK_APP_SCHEME = "hitsbankapp"
    const val DEEPLINK_EMPLOYEE_PART = "employee"
    const val DEEPLINK_CLIENT_PART = "client"
    const val DEEPLINK_AUTH_HOST = "authorized"

    const val TIMEOUT_SEC = 20L
    const val BASE_URL_WITHOUT_PORTS = "http://10.0.2.2"
    const val KEYCLOAK_BASE_URL = "$BASE_URL_WITHOUT_PORTS:8080/"
    const val BASE_URL = "$BASE_URL_WITHOUT_PORTS:9446/"
    const val AUTH_PAGE_PATH = "realms/bank/protocol/openid-connect/auth"
    const val AUTH_CLIENT_ID = "bank-rest-api"
    const val AUTH_REDIRECT_URI = "$DEEPLINK_APP_SCHEME$DEEPLINK_SCHEME_SEPARATOR$DEEPLINK_AUTH_HOST"
    const val AUTH_REDIRECT_URI_EMPLOYEE = "hitsbankapp://employee_authorized"

    const val GENERAL_ERROR_TEXT = "Что-то пошло не так…"

    const val DEFAULT_PAGE_SIZE = 10
}