package com.deathhit.lib_sign_able

import java.util.*

class SignAble<Content>(val content: Content? = null) {
    private val signatureMap: WeakHashMap<Any, Boolean> by lazy { WeakHashMap() }

    fun sign(any: Any, onSigned: (content: Content) -> Unit) {
        if (content == null || signatureMap[any] == true)
            return
        signatureMap[any] = true
        onSigned.invoke(content)
    }
}