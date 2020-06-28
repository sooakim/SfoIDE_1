package io.github.sooakim.sfoide.view.base

import androidx.fragment.app.Fragment
import kotlin.reflect.KClass

interface DialogFragmentLauncher<T: Fragment>{
    val fragmentClass: KClass<T>

    val tag: String?
        get() = fragmentClass.simpleName

    fun newInstance(): T
}