package com.deathhit.framework

import java.util.*
import kotlin.reflect.KProperty

open class ObjectPackage<Content> {
    private inner class ContentDelegate {
        private var value: Content? = null

        operator fun getValue(thisRef: Any?, property: KProperty<*>): Content? {
            return value
        }

        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Content?) {
            signatureMap.clear()
            this.value = value
        }
    }

    private val signatureMap: WeakHashMap<Any, Boolean> = WeakHashMap()

    var content: Content? by ContentDelegate()

    fun sign(any: Any?): Content? {
        return if (isSigned(any))
            null
        else {
            signatureMap[any] = true
            content
        }
    }

    private fun isSigned(any: Any?): Boolean {
        signatureMap[any]?.let { return it } ?: run { return false }
    }
}