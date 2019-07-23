package com.xuge.common

import com.xuge.common.ext.no
import com.xuge.common.ext.otherwise
import com.xuge.common.ext.yes
import org.junit.Assert
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun testBoolean() {
        val result1 = true.yes { 1 }.otherwise { 2 }

        Assert.assertEquals(result1, 1)

        val result2 = false.yes { 1 }.otherwise { 2 }
        Assert.assertEquals(result2, 2)

        val value3 = true.no { 1 } .otherwise { 2 }
        Assert.assertEquals(value3, 2)
    }

    fun getABoolean(): Boolean {
        return true
    }
}