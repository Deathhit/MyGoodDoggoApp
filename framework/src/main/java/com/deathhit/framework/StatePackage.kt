package com.deathhit.framework

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelStoreOwner
import java.util.*
import kotlin.reflect.KProperty

class StatePackage<Content> : Event<Content>, Status<Content> {
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

    override var content: Content? by ContentDelegate()

    override fun signForEvent(viewModelStoreOwner: ViewModelStoreOwner): Content? =
        sign(viewModelStoreOwner.viewModelStore)

    override fun signForStatus(activity: Activity): Content? = sign(activity)

    override fun signForStatus(fragment: Fragment): Content? = sign(fragment.view)

    private fun sign(any: Any?): Content? {
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