package com.xuge.imooc232

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.xuge.layout.v2.*

class TestActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        frameLayout {
            setBackgroundColor(Color.RED)

            verticalLayout {
                button {
                    text = "Button 1"
                    setBackgroundColor(Color.YELLOW)
                }.lparams {
                    weight = 1f
                }

                button {
                    text = "Button 2"
                    setBackgroundColor(Color.GREEN)
                }.lparams {
                    weight = 3f
                }
            }.lparams(height = MATCH_PARENT) {
                gravity = Gravity.RIGHT
            }
        }
    }
}