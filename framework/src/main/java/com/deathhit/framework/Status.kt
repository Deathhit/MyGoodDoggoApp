package com.deathhit.framework

import android.app.Activity
import androidx.fragment.app.Fragment

interface Status<Content> {
    fun signForStatus(activity: Activity): Content?
    fun signForStatus(fragment: Fragment): Content?
}