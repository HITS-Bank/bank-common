package ru.hitsbank.bank_common.data.repository

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import ru.hitsbank.bank_common.data.api.NotificationApi
import ru.hitsbank.bank_common.data.utils.apiCall
import ru.hitsbank.bank_common.data.utils.toCompletableResult
import ru.hitsbank.bank_common.domain.Completable
import ru.hitsbank.bank_common.domain.Result
import ru.hitsbank.bank_common.domain.model.RegisterFcmRequest
import ru.hitsbank.bank_common.domain.repository.INotificationRepository
import javax.inject.Inject

class NotificationRepository @Inject constructor(
    private val notificationApi: NotificationApi,
) : INotificationRepository {

    override suspend fun registerFcmToken(request: RegisterFcmRequest): Result<Completable> {
        return apiCall(Dispatchers.IO) {
            notificationApi.registerFcmToken(request)
                .toCompletableResult()
        }
    }

    override fun subscribeToTopic(topicName: String) {
        FirebaseMessaging.getInstance().subscribeToTopic(topicName)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "successfully received message from topic $topicName")
                } else {
                    Log.e(TAG, "error getting message from topic $topicName")
                }
            }
    }

    private companion object {
        const val TAG = "NotificationRepository"
    }
}