package com.xuge.imooc232.utils

import android.support.design.widget.NavigationView
import android.support.v4.view.ViewCompat
import android.view.View
import com.xuge.common.ext.otherwise
import com.xuge.common.ext.yes

inline fun NavigationView.doOnLayoutAvailable(crossinline block: () -> Unit) {
    ViewCompat.isLaidOut(this).yes {
        block()
    }.otherwise {
        addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(
                v: View?, left: Int, top: Int, right: Int, bottom: Int,
                oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int
            ) {
                removeOnLayoutChangeListener(this)
                block()
            }

        })
    }
}