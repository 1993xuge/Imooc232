package com.xuge.imooc232.presenter

import com.xuge.imooc232.model.account.AccountManager
import com.xuge.imooc232.view.LoginActivity
import com.xuge.mvp.impl.BasePresenter

class LoginPresenter : BasePresenter<LoginActivity>() {

    fun doLogin(name: String, passwd: String) {
        AccountManager.username = name
        AccountManager.passwd = passwd
        view.onLoginStart()
        AccountManager.login()
            .subscribe({
                view.onLoginSuccess()
            }, {
                view.onLoginError(it)
            })
    }

    fun checkUserName(name: String): Boolean {
        return name.isNotEmpty()
    }

    fun checkPasswd(passwd: String): Boolean {
        return passwd.isNotEmpty()
    }

    override fun onResume() {
        super.onResume()
        view.onDataInit(AccountManager.username, AccountManager.passwd)
    }
}