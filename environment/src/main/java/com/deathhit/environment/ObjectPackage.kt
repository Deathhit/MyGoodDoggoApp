package com.deathhit.environment

import kotlin.reflect.KProperty

open class ObjectPackage<Content, Signature> {
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

    private val signatureMap: HashMap<Signature, Boolean> = HashMap()

    var content: Content? by ContentDelegate()

    fun sign(signature: Signature): Content? {
        return if (isSigned(signature))
            null
        else {
            signatureMap[signature] = true
            content
        }
    }

    private fun isSigned(signature: Signature): Boolean {
        signatureMap[signature]?.let { return it } ?: kotlin.run { return false }
    }
}