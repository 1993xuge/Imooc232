package com.xuge.mvp.impl

import android.support.v4.app.Fragment
import com.xuge.mvp.IMvpView
import com.xuge.mvp.IPresenter
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.jvmErasure

abstract class BaseFragment<out P : BasePresenter<BaseFragment<P>>> : IMvpView<P>, Fragment() {
    override val presenter: P

    init {
        presenter = createPresenterKt()
        presenter.view = this
    }


    fun createPresenterKt(): P {
        sequence {
            // 表示当前的类
            var thisClass: KClass<*> = this@BaseFragment::class
            while (true) {
                yield(thisClass.supertypes)
                thisClass = thisClass.supertypes.firstOrNull()?.jvmErasure ?: break
            }
        }.flatMap {
            it.flatMap { it.arguments }.asSequence()
        }.first {
            it.type?.jvmErasure?.isSubclassOf(IPresenter::class) ?: false
        }.let {
            return it.type!!.jvmErasure.primaryConstructor!!.call() as P
        }
    }
}

class MainFragment : BaseFragment<MainPresenter>()

class MainPresenter : BasePresenter<MainFragment>()