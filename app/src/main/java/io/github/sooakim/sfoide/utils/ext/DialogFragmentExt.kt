package io.github.sooakim.sfoide.utils.ext

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

fun DialogFragment.showSafely(fragmentManager: FragmentManager?, tag: String?){
    if(fragmentManager == null) return
    if(!fragmentManager.isDestroyed && !fragmentManager.isStateSaved){
        this.show(fragmentManager, tag)
    }
}