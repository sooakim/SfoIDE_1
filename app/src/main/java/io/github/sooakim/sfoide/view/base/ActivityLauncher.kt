package io.github.sooakim.sfoide.view.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import kotlin.reflect.KClass

interface ActivityLauncher<T: Activity>{
    val activityClass: KClass<T>

    fun getIntent(context: Context?): Intent{
        return Intent(context, activityClass.java)
    }

    fun startActivity(context: Context?){
        context?.startActivity(getIntent(context))
    }

    fun startActivityWithExtra(context: Context?, extras: Bundle){
        context?.startActivity(getIntent(context).apply {
            putExtras(extras)
        })
    }
}