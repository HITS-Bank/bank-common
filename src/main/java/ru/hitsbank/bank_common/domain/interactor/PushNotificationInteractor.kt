package ru.hitsbank.bank_common.domain.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.hitsbank.bank_common.domain.Completable
import ru.hitsbank.bank_common.domain.State
import ru.hitsbank.bank_common.domain.model.RegisterFcmRequest
import ru.hitsbank.bank_common.domain.repository.INotificationRepository
import ru.hitsbank.bank_common.domain.toState
import javax.inject.Inject

const val OPERATIONS_TOPIC = "operations"

class PushNotificationInteractor @Inject constructor(
    private val notificationRepository: INotificationRepository,
) {

    fun registerFcmToken(request: RegisterFcmRequest): Flow<State<Completable>> = flow {
        emit(State.Loading)
        emit(notificationRepository.registerFcmToken(request).toState())
    }

    fun subscribeToOperationsTopic() {
        notificationRepository.subscribeToTopic(OPERATIONS_TOPIC)
    }
}