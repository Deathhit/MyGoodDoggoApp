package com.deathhit.framework

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelStoreOwner

class StatePackage<Content> : ObjectPackage<Content>(), Event<Content>, Status<Content> {
    override fun signForEvent(viewModelStoreOwner: ViewModelStoreOwner): Content? =
        sign(viewModelStoreOwner.viewModelStore)

    override fun signForStatus(activity: Activity): Content? = sign(activity)

    override fun signForStatus(fragment: Fragment): Content? = sign(fragment.view)
}