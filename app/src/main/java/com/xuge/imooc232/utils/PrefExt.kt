package com.xuge.imooc232.utils

import com.xuge.common.sharedpreferences.Preference
import com.xuge.imooc232.AppContext
import kotlin.reflect.jvm.jvmName

inline fun <reified R, T> R.pref(default: T) = Preference(AppContext, "", default, R::class.jvmName)