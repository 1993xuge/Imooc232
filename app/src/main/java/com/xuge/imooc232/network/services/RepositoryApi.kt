package com.xuge.imooc232.network.services

import com.xuge.imooc232.network.entities.Repository
import com.xuge.imooc232.network.entities.SearchRepositories
import com.xuge.imooc232.network.retrofit
import retrofit2.adapter.rxjava.GitHubPaging
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

interface RepositoryApi {

    @GET("/users/{owner}/repos?type=all")
    fun listRepositoriesOfUser(
        @Path("owner") owner: String,
        @Query("page") page: Int = 1,
        @Query("per_page") per_page: Int = 20
    ): Observable<GitHubPaging<Repository>>

    @GET("/search/repositories?order=desc&sort=updated")
    fun allRepositories(
        @Query("page") page: Int = 1,
        @Query("q") q: String,
        @Query("per_page") per_page: Int = 20
    ): Observable<SearchRepositories>
}

object RepositoryService : RepositoryApi by retrofit.create(RepositoryApi::class.java)