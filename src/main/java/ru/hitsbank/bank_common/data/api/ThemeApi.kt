package ru.hitsbank.bank_common.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.hitsbank.bank_common.data.model.ThemeModel

interface ThemeApi {

    @POST("/personalization/theme")
    suspend fun setTheme(@Query("channel") channel: String, @Body theme: ThemeModel): Response<ThemeModel>

    @GET("/personalization/theme")
    suspend fun getTheme(@Query("channel") channel: String): Response<ThemeModel>
}