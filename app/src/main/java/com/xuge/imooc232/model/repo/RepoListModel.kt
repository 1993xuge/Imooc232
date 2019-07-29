package com.xuge.imooc232.model.repo

import com.xuge.imooc232.model.page.ListPage
import com.xuge.imooc232.network.entities.Repository
import com.xuge.imooc232.network.entities.User
import com.xuge.imooc232.network.services.RepositoryService
import com.xuge.imooc232.utils.format
import retrofit2.adapter.rxjava.GitHubPaging
import rx.Observable
import java.util.*

class RepoListModel(val owner: User?) : ListPage<Repository>() {
    override fun getData(page: Int): Observable<GitHubPaging<Repository>> {
        return if (owner == null) {
            RepositoryService.allRepositories(page, "push:<" + Date().format("yyyy-MM-dd")).map { it.paging }
        } else {
            RepositoryService.listRepositoriesOfUser(owner.login, page)
        }
    }

}