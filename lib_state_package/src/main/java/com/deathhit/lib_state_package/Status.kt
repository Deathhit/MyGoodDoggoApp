package com.deathhit.lib_state_package

import androidx.fragment.app.Fragment

interface Status<Content> {
    val content: Content?
    fun signForStatus(any: Any, onSigned: (content: Content) -> Unit)
    fun signForViewStatus(fragment: Fragment, onSigned: (content: Content) -> Unit)
}