package com.deathhit.environment

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

object MetadataProvider {
    fun getMetadataString(context: Context, key: String): String {
        var applicationInfo: ApplicationInfo? = null
        try {
            applicationInfo = context.packageManager.getApplicationInfo(
                context.packageName,
                PackageManager.GET_META_DATA
            )
        } catch (ignored: PackageManager.NameNotFoundException) {
        }
        return applicationInfo!!.metaData.getString(key)!!
    }
}