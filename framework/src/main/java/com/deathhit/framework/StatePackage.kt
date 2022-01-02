package com.deathhit.framework

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelStoreOwner
import com.deathhit.environment.ObjectPackage

class StatePackage<Content> : ObjectPackage<Content, Int>(), Event<Content>, Status<Content> {
    override fun signForEvent(viewModelStoreOwner: ViewModelStoreOwner): Content? =
        sign(viewModelStoreOwner.viewModelStore.hashCode())

    override fun signForStatus(activity: Activity): Content? = sign(activity.hashCode())

    override fun signForStatus(fragment: Fragment): Content? = sign(fragment.view.hashCode())
}