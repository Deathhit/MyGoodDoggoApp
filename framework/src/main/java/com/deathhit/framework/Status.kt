package com.deathhit.framework

import android.app.Activity
import androidx.fragment.app.Fragment

interface Status<Content> {
    val content: Content?
    fun signForStatus(activity: Activity, onSigned: (content: Content) -> Unit)
    fun signForStatus(fragment: Fragment, onSigned: (content: Content) -> Unit)
}