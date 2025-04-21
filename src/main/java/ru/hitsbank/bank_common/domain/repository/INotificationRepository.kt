package ru.hitsbank.bank_common.domain.repository

import ru.hitsbank.bank_common.domain.Completable
import ru.hitsbank.bank_common.domain.Result
import ru.hitsbank.bank_common.domain.model.RegisterFcmRequest

interface INotificationRepository {

    suspend fun registerFcmToken(request: RegisterFcmRequest): Result<Completable>

    fun subscribeToTopic(topicName: String)
}