package ru.hitsbank.bank_common

object Constants {

    const val GENERAL_ERROR_TEXT = "Что-то пошло не так…"

    const val DEFAULT_PAGE_SIZE = 10

    const val CLIENT_APP_CHANNEL = "CLIENT"

    const val DEEPLINK_SCHEME_SEPARATOR = "://"
    const val DEEPLINK_PART_SEPARATOR = "/"

    const val DEEPLINK_APP_SCHEME = "hitsbankapp"
    const val DEEPLINK_EMPLOYEE_PART = "employee"
    const val DEEPLINK_CLIENT_PART = "client"
    const val DEEPLINK_AUTH_HOST = "authorized"

    const val TIMEOUT_SEC = 20L
    const val BASE_IP = "10.0.2.2"
    const val BASE_URL_WITHOUT_PORTS = "http://$BASE_IP"
    const val KEYCLOAK_PORT = 8080
    const val KEYCLOAK_BASE_URL = "$BASE_URL_WITHOUT_PORTS:$KEYCLOAK_PORT/"
    const val GATEWAY_PORT = 9446
    const val CORE_PORT = 9444
    const val BASE_URL = "$BASE_URL_WITHOUT_PORTS:$GATEWAY_PORT/"
    const val AUTH_PAGE_PATH = "realms/bank/protocol/openid-connect/auth"
    const val AUTH_CLIENT_ID = "bank-rest-api"
    const val AUTH_REDIRECT_URI = "$DEEPLINK_APP_SCHEME$DEEPLINK_SCHEME_SEPARATOR$DEEPLINK_AUTH_HOST"
    const val AUTH_REDIRECT_URI_EMPLOYEE = "hitsbankapp://employee_authorized"

    private const val WEBSOCKET_BASE_URL = "ws://$BASE_IP:$CORE_PORT/"
    private const val WEBSOCKET_ACCOUNT_HISTORY_PATH = "ws/websocket"
    const val WEBSOCKET_ACCOUNT_HISTORY_URI = "$WEBSOCKET_BASE_URL$WEBSOCKET_ACCOUNT_HISTORY_PATH"
    private const val WEBSOCKET_ACCOUNT_HISTORY_PATH_BEGINNING = "/topic/bank_account/"
    private const val WEBSOCKET_ACCOUNT_HISTORY_PATH_END = "/operation_history"

    fun websocketAccountHistoryTopic(accountId: String): String {
        return "$WEBSOCKET_ACCOUNT_HISTORY_PATH_BEGINNING$accountId$WEBSOCKET_ACCOUNT_HISTORY_PATH_END"
    }
}