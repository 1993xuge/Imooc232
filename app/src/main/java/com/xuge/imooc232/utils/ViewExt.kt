package com.xuge.imooc232.utils

import android.widget.TextView
import com.zzhoujay.richtext.RichText

var TextView.markdownText: String
    set(value) {
        RichText.fromMarkdown(value).into(this)
    }
    get() = text.toString()