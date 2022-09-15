package com.deathhit.data

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

internal fun getMetadataString(context: Context, key: String): String {
    var ai: ApplicationInfo? = null
    try {
        ai = context.packageManager.getApplicationInfo(
            context.packageName,
            PackageManager.GET_META_DATA
        )
    } catch (ignored: PackageManager.NameNotFoundException) {
    }
    return ai!!.metaData!!.getString(key)!!
}