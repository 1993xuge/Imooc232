package com.xuge.imooc232.view

import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.xuge.common.ext.otherwise
import com.xuge.common.ext.yes
import com.xuge.imooc232.R
import com.xuge.imooc232.presenter.LoginPresenter
import com.xuge.imooc232.utils.hideSoftInput
import com.xuge.mvp.impl.BaseActivity
import org.jetbrains.anko.toast

class LoginActivity : BaseActivity<LoginPresenter>() {

    private val signInButton by lazy { findViewById<Button>(R.id.signInButton) }
    private val username by lazy { findViewById<AutoCompleteTextView>(R.id.username) }
    private val password by lazy { findViewById<EditText>(R.id.password) }
    private val loginForm by lazy { findViewById<View>(R.id.loginForm) }
    private val loginProgress by lazy { findViewById<ProgressBar>(R.id.loginProgress) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signInButton.setOnClickListener {
            presenter.checkUserName(username.text.toString()).yes {
                presenter.checkPasswd(password.text.toString())
                    .yes {
                        hideSoftInput()
                        presenter.doLogin(username.text.toString(), password.text.toString())
                    }.otherwise {
                        showTips(password, "密码不合法")
                    }
            }.otherwise {
                showTips(password, "用户名不合法")
            }
        }
    }

    private fun showTips(view: EditText, tips: String) {
        view.requestFocus()
        view.error = tips
    }

    fun onLoginStart() {
        toast("登录开始")
    }

    fun onLoginError(e: Throwable) {
        e.printStackTrace()
        toast("登录失败")
    }

    fun onLoginSuccess() {
        toast("登陆成功")
    }

    fun onDataInit(name: String, passwd: String) {
        username.setText(name)
        password.setText(passwd)

    }
}
