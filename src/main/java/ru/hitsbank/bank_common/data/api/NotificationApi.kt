package ru.hitsbank.bank_common.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ru.hitsbank.bank_common.domain.model.RegisterFcmRequest

interface NotificationApi {

    @POST("notification/fcm/register")
    suspend fun registerFcmToken(@Body request: RegisterFcmRequest): Response<Unit>
}