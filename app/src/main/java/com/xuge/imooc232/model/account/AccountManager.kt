package com.xuge.imooc232.model.account

import com.google.gson.Gson
import com.xuge.imooc232.network.entities.AuthorizationReq
import com.xuge.imooc232.network.entities.AuthorizationRsp
import com.xuge.imooc232.network.entities.User
import com.xuge.imooc232.network.services.AuthService
import com.xuge.imooc232.network.services.UserService
import com.xuge.imooc232.utils.fromJson
import com.xuge.imooc232.utils.pref
import retrofit2.HttpException
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

interface OnAccountStateChangeListener {
    fun onLogin(user: User)

    fun onLogout()
}

object AccountManager {
    var authId by pref(-1)
    var username by pref("")

    var passwd by pref("")

    var token by pref("")

    private var userJson by pref("")

    var currentUser: User? = null
        get() {
            if (field == null && userJson.isNotEmpty()) {
                field = Gson().fromJson<User>(userJson)
            }

            return field
        }
        set(value) {
            if (value == null) {
                userJson = ""
            } else {
                userJson = Gson().toJson(value)
            }
            field = value
        }

    val onAccountStateChangeListeners = ArrayList<OnAccountStateChangeListener>()

    private fun notifyLogin(user: User) {
        onAccountStateChangeListeners.forEach { it.onLogin(user) }
    }

    private fun notifyLogout() {
        onAccountStateChangeListeners.forEach { it.onLogout() }
    }

    fun isLoggedIn(): Boolean = token.isNotEmpty()

    fun login() = AuthService.createAuthorization(AuthorizationReq())
        .observeOn(AndroidSchedulers.mainThread()) // 结果线程
        .subscribeOn(Schedulers.io()) // 执行线程
        .doOnNext {
            if (it.token.isEmpty()) throw AccountException(it)
        }
        .retryWhen {
            it.flatMap {
                if (it is AccountException) {
                    AuthService.deleteAuthorization(it.authorizationRsp.id)
                } else {
                    Observable.error(it)
                }
            }
        }
        .flatMap {
            token = it.token
            authId = it.id
            UserService.getAuthenticatedUser()
        }
        .map {
            currentUser = it
            notifyLogin(it)
        }

    fun logout() = AuthService.deleteAuthorization(authId)
        .subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(Schedulers.io())
        .doOnNext {
            if (it.isSuccessful) {
                authId = -1
                token = ""
                currentUser = null
                notifyLogout()
            } else {
                throw HttpException(it)
            }
        }

    class AccountException(val authorizationRsp: AuthorizationRsp) : Exception("Already logged in.")
}