package com.xuge.imooc232.utils

import cn.carbs.android.avatarimageview.library.AvatarImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun AvatarImageView.loadWithGlide(
    url: String,
    textPlaceHolder: Char,
    requestOptions: RequestOptions = RequestOptions()
) {
    textPlaceHolder.toString().let { setTextAndColorSeed(it.toUpperCase(), it.hashCode().toString()) }

    Glide.with(this.context).load(url).apply(requestOptions).into(this)
}