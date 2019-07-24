package com.xuge.imooc232.view

import android.os.Bundle
import com.xuge.imooc232.R
import com.xuge.imooc232.presenter.LoginPresenter
import com.xuge.mvp.impl.BaseActivity

class LoginActivity : BaseActivity<LoginPresenter>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}
