package com.xuge.imooc232.network.services

import com.xuge.imooc232.network.entities.AuthorizationReq
import com.xuge.imooc232.network.retrofit
import com.xuge.imooc232.settings.Configs
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Path
import rx.Observable

interface AuthApi {

    @PUT("/authorizations/clients/${Configs.Account.clientId}/{fingerPrint}")
    fun createAuthorization(
        @Body req: AuthorizationReq,
        @Path("fingerPrint") fingerPrint: String = Configs.Account.fingerPrint
    )

    @DELETE("/authorizations/{id}")
    fun deleteAuthorization(@Path("id") id: Int): Observable<Response<Any>>
}

object AuthService : AuthApi by retrofit.create(AuthApi::class.java)