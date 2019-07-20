package com.xuge.imooc232

import com.xuge.common.Preference

object Settings {

    var email: String by Preference(AppContext, "email", "")
    var password: String by Preference(AppContext, "password", "")
}
