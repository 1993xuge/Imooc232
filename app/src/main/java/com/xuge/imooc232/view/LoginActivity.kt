package com.xuge.imooc232.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.bennyhuo.tieguanyin.annotations.ActivityBuilder
import com.xuge.common.ext.otherwise
import com.xuge.common.ext.yes
import com.xuge.imooc232.R
import com.xuge.imooc232.presenter.LoginPresenter
import com.xuge.imooc232.utils.hideSoftInput
import com.xuge.mvp.impl.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast

@ActivityBuilder(flags = [Intent.FLAG_ACTIVITY_NO_HISTORY])
class LoginActivity : BaseActivity<LoginPresenter>() {

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

    private fun showProgress(show: Boolean) {
        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime)
        loginForm.animate().setDuration(shortAnimTime.toLong()).alpha(
            (if (show) 0 else 1).toFloat()
        ).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                loginForm.visibility = if (show) View.GONE else View.VISIBLE
            }
        })

        loginProgress.animate().setDuration(shortAnimTime.toLong()).alpha(
            (if (show) 1 else 0).toFloat()
        ).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                loginProgress.visibility = if (show) View.VISIBLE else View.GONE
            }
        })
    }

    private fun showTips(view: EditText, tips: String) {
        view.requestFocus()
        view.error = tips
    }

    fun onLoginStart() {
        toast("登录开始")
        showProgress(true)
    }

    fun onLoginError(e: Throwable) {
        e.printStackTrace()
        toast("登录失败")
        showProgress(false)
    }

    fun onLoginSuccess() {
        toast("登陆成功")
        showProgress(false)
        startMainActivity()
    }

    fun onDataInit(name: String, passwd: String) {
        username.setText(name)
        password.setText(passwd)

    }
}
