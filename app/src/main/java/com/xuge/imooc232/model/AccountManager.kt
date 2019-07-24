package com.xuge.imooc232.model.account

import com.xuge.imooc232.utils.pref

object AccountManager {
    var username by pref("")

    var passwd by pref("")

    var token by pref("")

    fun isLoggedIn(): Boolean = TODO()
}