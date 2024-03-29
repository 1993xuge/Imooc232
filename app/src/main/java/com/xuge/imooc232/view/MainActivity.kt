package com.xuge.imooc232.view

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.bennyhuo.github.view.fragments.AboutFragment
import com.bennyhuo.tieguanyin.annotations.ActivityBuilder
import com.xuge.common.ext.no
import com.xuge.common.ext.otherwise
import com.xuge.common.log.logger
import com.xuge.imooc232.R
import com.xuge.imooc232.model.account.AccountManager
import com.xuge.imooc232.model.account.OnAccountStateChangeListener
import com.xuge.imooc232.network.entities.User
import com.xuge.imooc232.network.services.RepositoryService
import com.xuge.imooc232.utils.doOnLayoutAvailable
import com.xuge.imooc232.utils.format
import com.xuge.imooc232.utils.loadWithGlide
import com.xuge.imooc232.utils.showFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.sdk15.listeners.onClick
import org.jetbrains.anko.toast
import org.slf4j.LoggerFactory
import java.util.*

@ActivityBuilder(flags = [Intent.FLAG_ACTIVITY_CLEAR_TOP])
class MainActivity : AppCompatActivity(), OnAccountStateChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.setDrawerListener(toggle)
        toggle.syncState()

        initNavigationView()

        AccountManager.onAccountStateChangeListeners.add(this)

        showFragment(R.id.fragmentContainer, AboutFragment::class.java)
        title = "About"

//        RepositoryService.listRepositoriesOfUser("1993xuge", 2, 20).subscribe({
//            logger.warn("Github Paging hasNext = ${it.hasNext}   Paging = $it")
//        }, {
//            it.printStackTrace()
//        })

        RepositoryService.allRepositories(2, "pushed:<" + Date().format("yyyy-MM-dd"))
            .subscribe({
                logger.debug("Paging: hasNext=${it.paging.hasNext}, hasPrev=${it.paging.hasPrev}")
            },{
                it.printStackTrace()
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        AccountManager.onAccountStateChangeListeners.remove(this)
    }

    private fun initNavigationView() {
        // 如果 AccountManager.currentUser ！= null，则进入let
        // 如果 AccountManager.currentUser = = null，那么 执行运算符?: 后面的逻辑
        AccountManager.currentUser?.let(::updateNavigationView) ?: clearNavigationView()
        initNavigationHeaderEvent()
    }

    private fun initNavigationHeaderEvent() {
        navigationView.doOnLayoutAvailable {
            navigationHeader.setOnClickListener {
                AccountManager.isLoggedIn()
                    .no {
                        startLoginActivity()
                    }
                    .otherwise {
                        AccountManager.logout().subscribe({
                            toast("注销成功")
                        }, {
                            it.printStackTrace()
                        })
                    }
            }
        }
    }

    private fun updateNavigationView(user: User) {
        navigationView.doOnLayoutAvailable {
            usernameView.text = user.login
            emailView.text = user.email ?: ""

            avatarView.loadWithGlide(user.avatar_url, user.login.first())
        }
    }

    private fun clearNavigationView() {
        navigationView.doOnLayoutAvailable {
            usernameView.text = "请登录"
            emailView.text = ""
            avatarView.imageResource = R.drawable.ic_github
        }
    }

    override fun onLogin(user: User) {
        updateNavigationView(user)
    }

    override fun onLogout() {
        clearNavigationView()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}