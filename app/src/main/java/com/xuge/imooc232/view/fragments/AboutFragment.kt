package com.bennyhuo.github.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xuge.imooc232.R
import com.xuge.imooc232.utils.markdownText
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk15.listeners.onClick
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.nestedScrollView

/**
 * Created by benny on 7/9/17.
 */
class AboutFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return AboutFragmentUI().createView(AnkoContext.Companion.create(context!!, this))
    }
}

class AboutFragmentUI : AnkoComponent<AboutFragment> {
    override fun createView(ui: AnkoContext<AboutFragment>): View {
        return ui.apply {
            nestedScrollView {
                verticalLayout {
                    imageView {
                        imageResource = R.mipmap.ic_launcher
                    }.lparams(width = wrapContent, height = wrapContent) {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }

                    textView("Github") {
                        textColor = R.color.colorPrimary

                    }.lparams(width = wrapContent, height = wrapContent) {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }

                    textView("By Xuge") {
                        textColor = R.color.colorPrimary

                    }.lparams(width = wrapContent, height = wrapContent) {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }

                    textView(R.string.open_source_licenses) {
                        textColor = R.color.colorPrimary
                        setOnClickListener {
                            alert {
                                customView {
                                    scrollView {
                                        textView {
                                            padding = dip(10)
                                            markdownText =
                                                context.assets.open("licenses.md").bufferedReader().readText()
                                        }
                                    }
                                }
                            }.show()
                        }
                    }.lparams(width = wrapContent, height = wrapContent) {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }
                }.lparams(width = wrapContent, height = wrapContent) {
                    gravity = Gravity.CENTER
                }
            }
        }.view
    }

}