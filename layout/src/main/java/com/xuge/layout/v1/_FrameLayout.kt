package com.xuge.layout.v1

import android.content.Context
import android.view.View
import android.widget.FrameLayout

class _FrameLayout(context: Context) : FrameLayout(context), DslViewParent<FrameLayout.LayoutParams> {

    var <T : View> T.layoutGravity: Int
        set(value) {
            lparams.gravity = value
        }
        get() {
            return lparams.gravity
        }

    override var <T : View> T.layoutWidth: Int
        set(value) {
            lparams.width = value
        }
        get() = lparams.width

    override var <T : View> T.layoutHeight: Int
        set(value) {
            lparams.height = value
        }
        get() = lparams.height

    val <T : View> T.x1024: Int
        get() = 1
}