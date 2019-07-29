package com.xuge.imooc232.model.page

import retrofit2.adapter.rxjava.GitHubPaging
import rx.Observable

interface DataProvider<DataType> {
    fun getData(page: Int): Observable<GitHubPaging<DataType>>
}