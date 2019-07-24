package com.xuge.imooc232.network.services

import com.xuge.imooc232.network.entities.User
import com.xuge.imooc232.network.retrofit
import retrofit2.http.GET
import rx.Observable

interface UserApi {

    @GET("/user")
    fun getAuthenticatedUser(): Observable<User>
}

object UserService : UserApi by retrofit.create(UserApi::class.java)

