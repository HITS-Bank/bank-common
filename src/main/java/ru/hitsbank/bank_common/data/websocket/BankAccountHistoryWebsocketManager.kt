package ru.hitsbank.bank_common.data.websocket

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.reactive.asFlow
import okhttp3.OkHttpClient
import ru.hitsbank.bank_common.Constants.WEBSOCKET_ACCOUNT_HISTORY_URI
import ru.hitsbank.bank_common.Constants.websocketAccountHistoryTopic
import ru.hitsbank.bank_common.data.model.OperationResponse
import ru.hitsbank.bank_common.di.AuthOkHttp
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BankAccountHistoryWebsocketManager @Inject constructor(
    private val gson: Gson,
    @AuthOkHttp private val okHttpClient: OkHttpClient,
) {

    private fun connectToWebsocket(): StompClient {
        val client = Stomp.over(
            Stomp.ConnectionProvider.OKHTTP,
            WEBSOCKET_ACCOUNT_HISTORY_URI,
            null,
            okHttpClient,
        )
        client.connect()
        return client
    }

    fun accountHistoryUpdatesFlow(accountId: String): Flow<OperationResponse> {
        val stompClient = connectToWebsocket()
        return stompClient
            .topic(websocketAccountHistoryTopic(accountId))
            .asFlow()
            .map { message ->
                gson.fromJson(message.payload, OperationResponse::class.java)
            }
            .onCompletion {
                stompClient.disconnect()
            }
    }
}