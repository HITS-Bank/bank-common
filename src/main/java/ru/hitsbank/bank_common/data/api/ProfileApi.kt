package ru.hitsbank.bank_common.data.api

import retrofit2.Response
import retrofit2.http.GET
import ru.hitsbank.bank_common.data.model.ProfileResponse

interface ProfileApi {

    @GET("users/profile")
    suspend fun getSelfProfile(): Response<ProfileResponse>
}